package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryPostRepo extends JpaRepository<PostCategory, Integer> {
    Optional<PostCategory> findBySlug(String slug);
}
