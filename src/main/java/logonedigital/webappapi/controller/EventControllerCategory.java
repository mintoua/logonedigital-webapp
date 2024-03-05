package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventCategoryReqDTO.EventCategoryDTO;
import logonedigital.webappapi.entity.EventCategory;
import logonedigital.webappapi.service.eventFeatures.eventCategory.EventCategoryService;
import logonedigital.webappapi.utils.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Event Category APIs")
public class EventControllerCategory {

    private final EventCategoryService eventCategoryService;



    @Operation(summary = "Add new CategoryEvent", description = "Add Category's event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully !"),
            @ApiResponse(responseCode = "400", description = "Event category already exist !"),
            @ApiResponse(responseCode = "403", description = "Access deny")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PostMapping(path = "/secure/categories_event/add")
    public ResponseEntity<String> addCategoryEvent(@Valid @RequestBody EventCategoryDTO eventCategoryDTO)
    {
        this.eventCategoryService.addCategoryEvent(eventCategoryDTO);
        return new ResponseEntity<>("Created successfully !", HttpStatus.CREATED);
    }

    @Operation(summary = "Get Event Category ", description = "Get Event Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok!"),
            @ApiResponse(responseCode = "404", description = "Resource not found!"),
    })
    @GetMapping(path = "/public/categories_event/{slug}")
    public ResponseEntity<EventCategory> getCategoryEvent(@PathVariable(name = "slug") String slug){
        return ResponseEntity
                .status(200)
                .body(this.eventCategoryService.getCategoryEvent(Tool.cleanIt(slug)));
    }

    @Operation(summary = "Get Events Category ", description = "Get Events Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok!"),
    })
    @GetMapping(path = "/public/categories_event")
    public ResponseEntity<List<EventCategory>> getCategoriesEvent(){
        return new ResponseEntity<>(this.eventCategoryService.getCategoriesEvent(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Event Category ", description = "Delete Event Category",
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok!"),
            @ApiResponse(responseCode = "404", description = "Resource not found!"),
            @ApiResponse(responseCode = "403", description = "Access deny !")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @DeleteMapping(path = "/secure/categories_event/delete/{slug}")
    public ResponseEntity<String> deleteCategoryEvent(@PathVariable(name = "slug") String slug){
        this.eventCategoryService.deleteCategoryEvent(Tool.cleanIt(slug));
        return ResponseEntity
                .status(202)
                .body("Element "+ Tool.cleanIt(slug) +" deleted successfully !");
    }

    @Operation(summary = "Edit Event Category ", description = "Edit Event Category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "ok!"),
            @ApiResponse(responseCode = "404", description = "Resource not found!"),
            @ApiResponse(responseCode = "403", description = "Access deny !")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PutMapping(path = "/secure/categories_event/edit/{slug}")
    public ResponseEntity<String> editCategoryEvent(@PathVariable(name = "slug") String slug,
                                           @Valid @RequestBody EventCategory eventCategory){
        this.eventCategoryService.editCategoryEvent(Tool.cleanIt(slug),eventCategory);
        return ResponseEntity.status(202).body("Edited successfully!");
    }
}
