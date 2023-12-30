package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryEventRepo extends JpaRepository<EventCategory, Integer> {
    Optional<EventCategory> findBySlug(String slug);
}
