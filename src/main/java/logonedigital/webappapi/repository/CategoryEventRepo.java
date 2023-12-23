package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.CategoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryEventRepo extends JpaRepository<CategoryEvent, Integer> {
    Optional<CategoryEvent> findBySlug(String slug);
}
