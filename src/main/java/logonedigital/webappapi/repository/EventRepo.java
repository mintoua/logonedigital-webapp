package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {
    Optional<Event> findBySlug(String slug);
}
