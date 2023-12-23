package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.service.event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/events")
@Tag(name = "Event APIs")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/add")
    public Event addEvent(@Valid @RequestBody Event event){
        return this.eventService.addEvent(event);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<Event> getEvents(){
        return this.eventService.getEvents();
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/{slug}")
    public Event getEvent(@PathVariable(name = "slug") String slug){
        return this.eventService.getEvent(slug);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping("/updated/{slug}")
    public Event updateEvent(@PathVariable(name = "slug") String slug, @Valid @RequestBody Event event){
        return this.eventService.editEvent(slug,event);
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @DeleteMapping("/deleted/{slug}")
    public String deleteEvent(@PathVariable(name = "slug") String slug){
        this.eventService.deletelEvent(slug);
        return "Event deleted successfully !";
    }
}
