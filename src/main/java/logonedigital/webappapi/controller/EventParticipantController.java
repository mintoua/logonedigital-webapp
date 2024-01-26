package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantReqDTO;
import logonedigital.webappapi.entity.EventParticipant;
import logonedigital.webappapi.service.eventFeatures.eventParticipants.EventParticipantService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@Tag(name = "Event Subscription APIs")
public class EventParticipantController {
    private final EventParticipantService eventParticipantService;

    public EventParticipantController(EventParticipantService eventParticipantService) {
        this.eventParticipantService = eventParticipantService;
    }
    //TODO ajouter un contrôle qui permettra de faire en sorte qu'une personne ne puisse pas s'inscrire 2 fois à un même évènement
    @Operation(summary = "Add new EventParticipant", description = "return EventParticipant")
    @ApiResponses(value = {
            //@ApiResponse(responseCode = "400", description = "This Post already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!"),
            @ApiResponse(responseCode = "400", description = "Fields validation failed!")
    })
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PostMapping(path = "public/event_participants/add")
    public ResponseEntity<EventParticipant> addEventParticipant(@RequestBody EventParticipantReqDTO EventParticipantReqDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.eventParticipantService.addEventParticipant(EventParticipantReqDTO));
    }

    @Operation(summary = "G EventParticipants", description = "return EventParticipant with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved!")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @GetMapping(path = "secure/event_participants/{offset}/{pageSize}")
    public ResponseEntity<Page<EventParticipant>> getEventParticipants(@PathVariable(name = "offset") int offset, @PathVariable(name = "pageSize") int pageSize){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventParticipantService.getEventParticipants(offset,pageSize));
    }

    @Operation(summary = "Get EventParticipant by ID", description = "return an EventParticipant as per ID")
    @ApiResponses(value = {
            //@ApiResponse(responseCode = "400", description = "This Post already exist!"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieve!"),
            @ApiResponse(responseCode = "404", description = "Not found - EventParticipant not found!")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTION')")
    @GetMapping(path = "secure/event_participants/{id}")
    public ResponseEntity<EventParticipant> getEventParticipant(@PathVariable(name = "id") Integer id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.eventParticipantService.getEventParticipant(id));
    }

    @Operation(summary = "Edit EventParticipant by ID", description = "return edited EventParticipant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully edited!"),
            @ApiResponse(responseCode = "400", description = "Fields validation failed!")
    })
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DIRECTION')")
    @PutMapping(path = "public/event_participants/updated/{id}")
    public ResponseEntity<EventParticipant> updateEventParticipant(@PathVariable(name = "id") Integer id,
                                                   @RequestBody EventParticipant eventParticipant){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(this.eventParticipantService.updateEventParticipant(id, eventParticipant));
    }

    @Operation(summary = "Deleted EventParticipant by ID", description = "return EventParticipant successfully deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully deleted!"),
    })
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_DIRECTION')")
    @DeleteMapping(path = "public/event_participants/deleted/{id}")
    public ResponseEntity<String> deleteEventParticipant(@PathVariable(name = "id") Integer id){
        this.eventParticipantService.deleteEventParticipant(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("This subscription deleted successfully !");
    }
}
