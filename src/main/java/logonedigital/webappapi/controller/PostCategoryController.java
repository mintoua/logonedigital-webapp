package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.blogFeaturesDTO.PostCategoryReqDTO;
import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.service.blogFeatures.postCategory.CategoryPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/categories_post")
@Slf4j
@Tag(name="PostsCategory APIs")
public class PostCategoryController {
    private final CategoryPostService categoryPostService;

    public PostCategoryController(CategoryPostService categoryPostService) {
        this.categoryPostService = categoryPostService;
    }

    @Operation(summary = "add new PostCategory", description = "return PostCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "This PostCategory already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!")
    })
    @PostMapping
    public ResponseEntity<PostCategory> addPostCategory(@Valid @RequestBody PostCategoryReqDTO postCategoryReqDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.categoryPostService.addCategoryPost(postCategoryReqDTO));
    }

    @Operation(summary = "Fetch all PostsCategory", description = "return Paginate PostCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<Page<PostCategory>> getCategoriesPost(@PathVariable(name = "offset") int offset,
                                                                @PathVariable(name = "pageSize") int pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryPostService.getCategoriesPost( offset, pageSize));
    }

    @Operation(summary = "Get PostCategory by slug", description = "return a PostCategory as per slug ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!"),
            @ApiResponse(responseCode = "404", description = "Not found - The PostCategory wasn't found")
    })
    @GetMapping("/{slug}")
    public ResponseEntity<PostCategory> getCategoryPost(@PathVariable(name = "slug") String slug){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.categoryPostService.getCategoryPost(slug));
    }

    @Operation(summary = "Delete PostCategory by slug", description = "return PostCategory deleted successfully!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Post Category's deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - The PostCategory wasn't found")
    })
    @DeleteMapping("/{slug}")
    public ResponseEntity<String> deletedCategoryPost(@PathVariable(name = "slug") String slug){
        this.categoryPostService.deleteCategoryPost(slug);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Post Category's deleted successfully!");
    }

    @Operation(summary = "Edit PostCategory by slug", description = "return PostCategory edited successfully as per slug!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Post Category's edited successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - The PostCategory wasn't found")
    })
    @PutMapping("/{slug}")
    public ResponseEntity<PostCategory> editCategoryPost(@PathVariable(name = "slug") String slug, @Valid @RequestBody PostCategory postCategory){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(this.categoryPostService.editCategoryPost(postCategory,slug));
    }
}
