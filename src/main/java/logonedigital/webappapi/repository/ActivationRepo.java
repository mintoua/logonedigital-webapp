package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Activation;
import logonedigital.webappapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ActivationRepo extends JpaRepository<Activation, Integer> {
    Optional<Activation> findByActivationCode(String code);
    Optional<Activation> findByUser(User user);
    void deleteAllByExpiredAtBefore(Instant now);
}
