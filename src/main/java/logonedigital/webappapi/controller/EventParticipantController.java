package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import logonedigital.webappapi.entity.EventParticipant;
import logonedigital.webappapi.service.eventFeatures.eventParticipants.EventParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/event_participants")
@Tag(name = "Event Subscription APIs")
public class EventParticipantController {
    private final EventParticipantService eventParticipantService;

    public EventParticipantController(EventParticipantService eventParticipantService) {
        this.eventParticipantService = eventParticipantService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/add")
    public EventParticipant addEventParticipant(@RequestBody EventParticipant eventParticipant){
        return this.eventParticipantService.addEventParticipant(eventParticipant);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EventParticipant> getEventParticipants(){
        return this.eventParticipantService.getEventParticipants();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public EventParticipant getEventParticipant(@PathVariable(name = "id") Integer id){
        return this.eventParticipantService.getEventParticipant(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/updated/{id}")
    public EventParticipant updateEventParticipant(@PathVariable(name = "id") Integer id,
                                                   @RequestBody EventParticipant eventParticipant){
        return this.eventParticipantService.updateEventParticipant(id, eventParticipant);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/deleted/{id}")
    public String deleteEventParticipant(@PathVariable(name = "id") Integer id){
        this.eventParticipantService.deleteEventParticipant(id);
        return "This subscription deleted successfully !";
    }
}
