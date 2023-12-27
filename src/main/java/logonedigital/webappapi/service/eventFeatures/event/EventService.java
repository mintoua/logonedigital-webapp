package logonedigital.webappapi.service.eventFeatures.event;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.entity.Event;

import java.util.List;

public interface EventService {
    Event addEvent(EventRequestDTO eventRequestDTO);
    List<Event> getEvents();
    Event getEvent(String slug);
    void deletelEvent(String slug);
    Event editEvent(String slug, EventRequestDTO eventRequestDTO);
}
