package logonedigital.webappapi.service.eventFeatures.event;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.entity.Event;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface EventService {
    Event addEvent(EventRequestDTO eventRequestDTO) throws IOException;
    Page<Event> getEvents(int offset, int pageSize);
    Event getEvent(String slug);
    void deletelEvent(String slug);
    Event editEvent(String slug, EventRequestDTO eventRequestDTO) throws IOException;
    Page<Event> getEventsByEventCategory(String slug, int offset, int pageSize);
    Page<Event> getPublishedEvents(int offset, int pageSize);
}
