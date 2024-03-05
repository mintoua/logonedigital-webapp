package logonedigital.webappapi.repository;

import logonedigital.webappapi.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    Optional<Post> findBySlug(String slug);

    @Query("select p from Post p where p.title = :title")
    Optional<Post> fetchPostByTitle(@Param("title") String title);

    @Query("select p from  Post p where p.published=true")
    Page<Post> fetchPostByPublished(Pageable pageable);

    @Query("select p from Post p where p.postCategory.slug = :postCategorySlug and p.published=true")
    Page<Post> fetchPostByCategory(@Param("postCategorySlug") String postCategorySlug, Pageable pageable);
}
