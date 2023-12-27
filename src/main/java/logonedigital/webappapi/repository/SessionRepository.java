package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {
}
