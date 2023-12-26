package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Integer> {
}
