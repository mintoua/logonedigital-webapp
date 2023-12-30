package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventCategoryReqDTO.EventCategoryDTO;
import logonedigital.webappapi.entity.EventCategory;
import logonedigital.webappapi.service.eventFeatures.eventCategory.EventCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/categories_event")
@Slf4j
@Tag(name="EventCategory APIs")
public class EventControllerCategory {

    private final EventCategoryService eventCategoryService;


    public EventControllerCategory(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @Operation(summary = "Add new CategoryEvent", description = "Add Category's event")
    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventCategory> addCategoryEvent(@Valid @ModelAttribute EventCategoryDTO eventCategoryDTO) throws IOException
    {

        return new ResponseEntity<>(this.eventCategoryService.addCategoryEvent(eventCategoryDTO), HttpStatus.OK);
    }

    @GetMapping(path = "/{slug}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public EventCategory getCategoryEvent(@PathVariable(name = "slug") String slug){
        return this.eventCategoryService.getCategoryEvent(slug);
    }

    @GetMapping
    public ResponseEntity<List<EventCategory>> getCategoriesEvent(){
        return new ResponseEntity<>(this.eventCategoryService.getCategoriesEvent(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{slug}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String deleteCategoryEvent(@PathVariable(name = "slug") String slug){
        this.eventCategoryService.deleteCategoryEvent(slug);
        return "Element "+ slug +" deleted successfully !";
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping(path = "/edit/{slug}")
    public EventCategory editCategoryEvent(@PathVariable(name = "slug") String slug,
                                           @Valid @RequestBody EventCategory eventCategory){
        return this.eventCategoryService.editCategoryEvent(slug, eventCategory);
    }
}
