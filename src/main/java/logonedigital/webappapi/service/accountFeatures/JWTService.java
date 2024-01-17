package logonedigital.webappapi.service.accountFeatures;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JWTService {

    private final Environment environment;
    private final UserRepo userRepo;

    public Map<String,String > generateToken(String username){
        User user = userRepo.findByEmail(username)
                .orElseThrow(()->new RessourceNotFoundException("User not found !"));
        return this.buildJwtToken(user);
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

        return  Map.of("bearer",bearer);
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
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
