package logonedigital.webappapi.service.accountFeatures;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import logonedigital.webappapi.entity.AccessToken;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.AccessTokenRepo;
import logonedigital.webappapi.repository.UserRepo;
import logonedigital.webappapi.security.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class JWTService {

    public static final String BEARER = "bearer";
    private final Environment environment;
    private final UserRepo userRepo;
    private final AccessTokenRepo accessTokenRepo;
    private void disableToken(User user){
        final List<AccessToken> accessTokenList = this.accessTokenRepo.fetchAllAccessTokenByEmail(user.getEmail()).peek(
                jwt -> {
                    jwt.setIsEnabled(true);
                    jwt.setIsExpired(true);
                }
        ).toList();

        this.accessTokenRepo.saveAll(accessTokenList);
    }

    public Map<String,String > generateToken(String username){
        User user = userRepo.findByEmail(username)
                .orElseThrow(()->new RessourceNotFoundException("User not found !"));
        this.disableToken(user);
        Map<String, String> jwtMap = this.buildJwtToken(user);
        //TODO à l'aide d'un cronjob desactivé le token lorsque la date d'expiration est atteinte
        AccessToken accessToken = new AccessToken();
        accessToken.setValue(jwtMap.get(BEARER));
        accessToken.setIsExpired(false);
        accessToken.setIsEnabled(false);
        accessToken.setUser(user);
        accessToken.setCreatedAt(Instant.now());
        this.accessTokenRepo.save(accessToken);
        return jwtMap;
    }

    private Map<String, String> buildJwtToken(User user){
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime * 30 * 60;

        Map<String, Object>claims = Map.of(
                "firstname",user.getFirstname(),
                "lastname",user.getLastname(),
                "email",user.getEmail(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT,user.getEmail()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();

        return  Map.of(BEARER,bearer);
    }

    private Key getKey() {

        final byte[] decode = Decoders.BASE64.decode(this.environment.getProperty("encryption.key"));
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token){
        return this.getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token){

        return this.getClaim(token, Claims::getExpiration).before(new Date());
    }

//    private Date extractExpirationDateFromToken(String token){
//        return this.getClaim(token, Claims::getExpiration);
//    }

    private <T> T getClaim(String token, Function<Claims,T> function){
        return function.apply(this.getAllClaims(token));
    }

    private Claims getAllClaims(String token){
        //todo mettre une exception pour gérer les erreurs lors de la manipution des tokens
        //todo gérer les exceptions liées à la corruption d'un token
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public AccessToken getTokenByValue(String value) throws RessourceNotFoundException {
        return this.accessTokenRepo
                .findByValueAndIsEnabledAndIsExpired(value,false, false)
                .orElseThrow(()->new RessourceNotFoundException("Please provide correct access token!"));
    }

    public void logout() {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccessToken accessToken = this.accessTokenRepo
                .fetchValidAccessTokenByUser(user.getUsername(),false, false)
                .orElseThrow(()-> new RessourceNotFoundException("Please provide correct access token"));
        accessToken.setIsEnabled(true);
        accessToken.setIsExpired(true);
        this.accessTokenRepo.save(accessToken);
    }

    @Scheduled(cron = "@daily")
    //@Scheduled(cron = "* */1 * * * *")
    public void removeUselessAccessToken(){
        log.info("Suppression des tokens à {}", Instant.now());
        this.accessTokenRepo.deleteAllByIsEnabledAndIsExpired(true,true);
    }
}
