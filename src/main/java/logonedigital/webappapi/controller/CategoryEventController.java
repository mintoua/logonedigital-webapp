package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.entity.CategoryEvent;
import logonedigital.webappapi.service.categoryEvent.CategoryEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories_event")
@Slf4j
@Tag(name="EventCategory APIs")
public class CategoryEventController {

    private final CategoryEventService categoryEventService;

    public CategoryEventController(CategoryEventService categoryEventService) {
        this.categoryEventService = categoryEventService;
    }

    @Operation(summary = "Add new CategoryEvent", description = "Add Category's event")
    @PostMapping(path = "/add")
    public ResponseEntity<CategoryEvent> addCategoryEvent(@Valid @RequestBody  CategoryEvent categoryEvent)
    {
        log.error("categoryEvent {}", categoryEvent);
        return new ResponseEntity<>(this.categoryEventService.addCategoryEvent(categoryEvent), HttpStatus.OK);
    }

    @GetMapping(path = "/{slug}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public CategoryEvent getCategoryEvent(@PathVariable(name = "slug") String slug){
        return this.categoryEventService.getCategoryEvent(slug);
    }

    @GetMapping
    public ResponseEntity<List<CategoryEvent>> getCategoriesEvent(){
        return new ResponseEntity<>(this.categoryEventService.getCategoriesEvent(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{slug}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String deleteCategoryEvent(@PathVariable(name = "slug") String slug){
        this.categoryEventService.deleteCategoryEvent(slug);
        return "Element "+ slug +" deleted successfully !";
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping(path = "/edit/{slug}")
    public CategoryEvent editCategoryEvent(@PathVariable(name = "slug") String slug,
                                           @Valid @RequestBody  CategoryEvent categoryEvent){
        return this.categoryEventService.editCategoryEvent(slug,categoryEvent);
    }
}
