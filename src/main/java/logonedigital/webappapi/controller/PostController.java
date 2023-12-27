package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import logonedigital.webappapi.service.blogFeatures.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/posts")
@Slf4j
@Tag(name="Post APIs")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public Post addPost(@Valid @RequestBody PostReqDTO postReqDTO){
        return this.postService.addPost(postReqDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    List<Post> getPosts(){
        return this.postService.getPosts();
    }

    @GetMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Post getPost(@PathVariable(name = "slug") String slug){
        return this.postService.getPost(slug);
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ResponseBody
    public String deletePost(@PathVariable(name = "slug") String slug){
        this.postService.deletePost(slug);
        return "Post deleted successfully !";
    }

    @PutMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ResponseBody

    public Post updatePost(@PathVariable(name = "slug") String slug,
                           @Valid @RequestBody PostReqDTO postReqDTO){
        return this.postService.editPost(postReqDTO, slug);
    }
}
