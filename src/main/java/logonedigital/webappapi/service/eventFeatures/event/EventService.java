package logonedigital.webappapi.service.event;

import logonedigital.webappapi.entity.Event;

import java.util.List;

public interface EventService {
    Event addEvent(Event event);
    List<Event> getEvents();
    Event getEvent(String slug);
    void deletelEvent(String slug);
    Event editEvent(String slug, Event event);
}
