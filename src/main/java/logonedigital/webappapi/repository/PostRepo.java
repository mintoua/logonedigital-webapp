package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    Optional<Post> findBySlug(String slug);
}
