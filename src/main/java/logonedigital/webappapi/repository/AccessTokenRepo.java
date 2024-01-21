package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface AccessTokenRepo extends JpaRepository<AccessToken, Integer> {
    Optional<AccessToken> findByValueAndIsEnabledAndIsExpired(String token, Boolean isEnabled, Boolean isExpired);
    @Query("select a from AccessToken a where a.user.email=:email AND a.isExpired=:isExpired and a.isEnabled=:isEnabled")
    Optional<AccessToken> fetchValidAccessTokenByUser(@Param("email") String email,
                                                      @Param("isEnabled") Boolean isEnabled,
                                                      @Param("isExpired") Boolean isExpired);

    @Query("select a from AccessToken a where a.user.email=:email")
    Stream<AccessToken> fetchAllAccessTokenByEmail(String email);

    void deleteAllByIsEnabledAndIsExpired(Boolean isExpired, Boolean isEnable);
}
