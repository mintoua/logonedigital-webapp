package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCategoryRepo extends JpaRepository<PostCategory, Integer> {
    Optional<PostCategory> findBySlug(String slug);
    @Query("select cp from PostCategory cp where cp.title = :title")
    Optional <PostCategory> fetchCategoryPostByTitle(@Param("title") String title);
}
