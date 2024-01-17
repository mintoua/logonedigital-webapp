package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Activation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationRepo extends JpaRepository<Activation, Integer> {
    Optional<Activation> findByActivationCode(String code);
}
