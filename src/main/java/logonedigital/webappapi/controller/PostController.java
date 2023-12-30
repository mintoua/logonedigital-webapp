package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import logonedigital.webappapi.service.blogFeatures.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Operation(summary = "add new Post", description = "return Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "This Post already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!"),
            @ApiResponse(responseCode = "400", description = "Fields validation failed!")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> addPost(@Valid @ModelAttribute  PostReqDTO postReqDTO) throws IOException {
       //log.error("{}", postReqDTO.getImageUrl());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.postService.addPost(postReqDTO));
    }


    @Operation(summary = "Get all posts", description = "return list of posts with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!"),
    })
    @Parameters(value = {
            @Parameter(name = "offset", description = "offset value can't be less than 1", example = "1,2,...")
    }
    )
    @GetMapping("/{offset}/{pageSize}")
    ResponseEntity<Page<Post>> getPosts(@PathVariable(name = "offset") int offset, @PathVariable(name = "pageSize") int pageSize){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.postService.getPosts(offset,pageSize));
    }

    @Operation(summary = "Get Post by Slug", description = "return a Post as per slug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!"),
            @ApiResponse(responseCode = "400", description = "Not found - Post doesn't exist inside data base")
    })
    @GetMapping("/{slug}")
    public ResponseEntity<Post> getPost(@PathVariable(name = "slug") String slug){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.postService.getPost(slug));
    }

    @Operation(summary = "Delete Post by Slug", description = "Post successfully deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted!"),
            @ApiResponse(responseCode = "400", description = "Not found - Post doesn't exist inside data base")
    })
    @DeleteMapping("/{slug}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "slug") String slug){
        this.postService.deletePost(slug);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Post deleted successfully !");
    }

    @Operation(summary = "Edit Post by Slug", description = "Post successfully edited")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully edited!"),
            @ApiResponse(responseCode = "400", description = "Not found - Post doesn't exist inside data base")
    })
    @PutMapping(path = "/{slug}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> updatePost(@PathVariable(name = "slug") String slug,
                                           @Valid @ModelAttribute  PostReqDTO postReqDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(this.postService.editPost(postReqDTO, slug));
    }

    @Operation(summary = "Get Posts by postCategory", description = "retrieve successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully retrieve!"),
            @ApiResponse(responseCode = "400", description = "Not found - Post doesn't exist inside data base")
    })
    @Parameters(value = {
            @Parameter(name = "offset", description = "offset value can't be less than 1", example = "1,2,...")
    }
    )
    @GetMapping("/{slug}/{offset}/{pageSize}")
    public ResponseEntity<Page<Post>> fetchPostByPostCategory(@PathVariable(name = "slug") String postCategorySlug,
                                                              @PathVariable(name = "offset") int offset,
                                                              @PathVariable(name = "pageSize") int pageSize)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.postService.getPostsByCategory(postCategorySlug,offset,pageSize));
    }
}
