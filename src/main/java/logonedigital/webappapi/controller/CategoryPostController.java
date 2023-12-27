package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.service.blogFeatures.CategoryPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories_post")
@Slf4j
@Tag(name="CategoryPost APIs")
public class CategoryPostController {
    private final CategoryPostService categoryPostService;

    public CategoryPostController(CategoryPostService categoryPostService) {
        this.categoryPostService = categoryPostService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public PostCategory addCategoryPost(@Valid @RequestBody PostCategory postCategory) {
        return this.categoryPostService.addCategoryPost(postCategory);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<PostCategory> getCategoriesPost() {
        return this.categoryPostService.getCategoriesPost();
    }

    @GetMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public PostCategory getCategoryPost(@PathVariable(name = "slug") String slug){
        return this.categoryPostService.getCategoryPost(slug);
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ResponseBody
    public String deletedCategoryPost(@PathVariable(name = "slug") String slug){
        this.categoryPostService.deleteCategoryPost(slug);
        return " post Category's deleted successfully !";
    }

    @PutMapping("/{slug}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ResponseBody
    public PostCategory editCategoryPost(@PathVariable(name = "slug") String slug, @Valid @RequestBody PostCategory postCategory){
        return this.categoryPostService.editCategoryPost(postCategory,slug);
    }
}
