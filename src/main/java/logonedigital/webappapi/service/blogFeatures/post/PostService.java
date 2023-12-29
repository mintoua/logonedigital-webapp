package logonedigital.webappapi.service.blogFeatures.post;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;

import java.io.IOException;
import java.util.List;

public interface PostService  {
    Post addPost(PostReqDTO postReqDTO) throws IOException;
    List<Post> getPosts();
    Post getPost(String slug);
    void deletePost(String slug);
    Post editPost(PostReqDTO postReqDTO, String slug);

}
