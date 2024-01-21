package logonedigital.webappapi.service.accountFeatures;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logonedigital.webappapi.entity.AccessToken;
import logonedigital.webappapi.exception.InvalidCredentialException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.AccessTokenRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Lazy
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final AccessTokenRepo accessTokenRepo;
    private static final List<String> PATHS_TO_SKIP = Arrays.asList("/swagger-ui/**", "/api/account/login", "/api/categories_post/**");
    private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/swagger-ui/**","");



//    private boolean shouldSkipTokenFilter(String requestURI) {
//
//        return PATHS_TO_SKIP.stream().anyMatch(path-> path.matches(requestURI));
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws RessourceNotFoundException, ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        boolean isTokenExpired = true;
        AccessToken jwtDB = new AccessToken();
        String token = null;


        if(StringUtils.isNotEmpty(authHeader) && StringUtils.startsWith(authHeader,"Bearer ")){
            token = authHeader.substring(7);
            jwtDB = this.jwtService.getTokenByValue(token);
            username = this.jwtService.extractUsername(token);
            isTokenExpired = this.jwtService.isTokenExpired(token);
        }

        if(StringUtils.isNotEmpty(username)
                && jwtDB.getUser().getEmail().equals(username)
                    && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(isTokenExpired)
                    throw new InvalidCredentialException("Token expired");
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);
        }




        filterChain.doFilter(request,response);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String requestURI = request.getRequestURI();
//
//        // Specify conditions under which the filter should be skipped
//        return requestURI.contains("/public/**");
//    }
}
