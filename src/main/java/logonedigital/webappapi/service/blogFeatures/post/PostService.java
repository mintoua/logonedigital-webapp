package logonedigital.webappapi.service.blogFeatures.post;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;


public interface PostService  {
    Post addPost(PostReqDTO postReqDTO) throws IOException;
    Page<Post> getPosts(int offset, int pageSize);
    Post getPost(String slug);
    void deletePost(String slug);
    Post editPost(PostReqDTO postReqDTO, String slug) throws IOException;
    Page<Post> getPostsByCategory(String postCategorySlug, int offset, int pageSize);
    Page<Post> getPublishedPost(int offset, int pageSize);

}
