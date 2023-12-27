package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
}
