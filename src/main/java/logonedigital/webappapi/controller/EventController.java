package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.service.eventFeatures.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Tag(name = "Event APIs")
public class EventController {

    private final EventService eventService;



    @Operation(summary = "Add new Event", description = "return Event",
            security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "This Post already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!"),
            @ApiResponse(responseCode = "400", description = "Fields validation failed!")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PostMapping(path = "secure/events/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addEvent(@Valid @ModelAttribute EventRequestDTO eventRequestDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Event added successfully!");
    }

    @Operation(summary = "Get Events", description = "return Events with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @GetMapping("public/events/{offset}/{pageSize}")
    public ResponseEntity<Page<Event>> getEvents(@PathVariable(name = "offset") int offset,
                                 @PathVariable(name = "pageSize") int pageSize)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventService.getEvents(offset, pageSize));
    }

    @Operation(summary = "Get Events", description = "return Events with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @GetMapping("public/events/{offset}/{pageSize}")
    public ResponseEntity<Page<Event>> getPublishedEvents(@PathVariable(name = "offset") int offset,
                                                 @PathVariable(name = "pageSize") int pageSize)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventService.getPublishedEvents(offset, pageSize));
    }

    @Operation(summary = "Get Events by EventCategory", description = "return Events with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!")
    })
    @GetMapping("public/events/fetch_event_by_event_category/{eventCategorySlug}/{offset}/{pageSize}")
    public ResponseEntity<Page<Event>> getEventByEventCategory(@PathVariable(name = "eventCategorySlug") String slug,
                                                               @PathVariable(name = "offset") int offset,
                                                               @PathVariable(name = "pageSize") int pageSize)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventService.getEventsByEventCategory(slug,offset,pageSize));
    }
    @Operation(summary = "Get an event by slug", description = "return an Event as per slug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved!"),
            @ApiResponse(responseCode = "404", description = "Not found - Event not found!")
    })
    @GetMapping("public/events/{slug}")
    public ResponseEntity<Event> getEvent(@PathVariable(name = "slug") String slug){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventService.getEvent(slug));
    }

    @Operation(summary = "Edit an event by slug", description = "return an Event", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully edited!"),
            @ApiResponse(responseCode = "404", description = "Not found - Event not found!")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PutMapping(path = "secure/events/updated/{slug}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateEvent(@PathVariable(name = "slug") String slug,
                             @ModelAttribute EventRequestDTO eventRequestDTO) throws IOException {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Event edited successfully !");
    }

    @Operation(summary = "Delete an event by slug", description = "return event deleted successfully", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted!"),
            @ApiResponse(responseCode = "404", description = "Not found - Event not found!")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @DeleteMapping("secure/events/deleted/{slug}")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "slug") String slug){
        this.eventService.deletelEvent(slug);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Event deleted successfully !");
    }
}
