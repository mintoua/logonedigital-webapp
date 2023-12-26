package logonedigital.webappapi.service.eventFeatures.event;

import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.EventRepo;
import logonedigital.webappapi.utils.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements logonedigital.webappapi.service.event.EventService {
    private final EventRepo eventRepo;

    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public Event addEvent(Event event) {
        event.setSlug(Tool.slugify(event.getTitre()));

        return this.eventRepo.save(event);
    }

    @Override
    public List<Event> getEvents() {
        return this.eventRepo.findAll();
    }

    @Override
    public Event getEvent(String slug) {
        Optional<Event> event = this.eventRepo.findBySlug(slug);
        if(event.isEmpty())
            throw new RessourceNotFoundException("This Event doesn't exist !");
        return event.get();
    }

    @Override
    public void deletelEvent(String slug) {
        Optional<Event> event = this.eventRepo.findBySlug(slug);
        if(event.isEmpty())
            throw new RessourceNotFoundException("This Event doesn't exist !");
        this.eventRepo.delete(event.get());

    }

    @Override
    public Event editEvent(String slug, Event event) {
        Optional<Event> eventDB = this.eventRepo.findBySlug(slug);
        //check if event doesn't exist to throw exception
        if(eventDB.isEmpty())
            throw new RessourceNotFoundException("This Event doesn't exist !");

        //updated event information
        eventDB.get().setTitre(event.getTitre());
        eventDB.get().setDuree(event.getDuree());
        eventDB.get().setDescription(event.getDescription());
        eventDB.get().setDetails(event.getDetails());
        eventDB.get().setLieu(event.getLieu());
        eventDB.get().setDateDeb(event.getDateDeb());
        eventDB.get().setDateFin(event.getDateFin());

        return this.eventRepo.saveAndFlush(eventDB.get());
    }
}
