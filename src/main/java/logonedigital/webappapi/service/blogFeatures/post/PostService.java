package logonedigital.webappapi.service.blogFeatures.post;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;

import java.util.List;

public interface PostService  {
    Post addPost(PostReqDTO postReqDTO);
    List<Post> getPosts();
    Post getPost(String slug);
    void deletePost(String slug);
    Post editPost(PostReqDTO postReqDTO, String slug);
}
