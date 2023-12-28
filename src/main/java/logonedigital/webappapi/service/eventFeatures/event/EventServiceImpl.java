package logonedigital.webappapi.service.eventFeatures.event;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.entity.CategoryEvent;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.EventFeatureMapper;
import logonedigital.webappapi.repository.CategoryEventRepo;
import logonedigital.webappapi.repository.EventRepo;
import logonedigital.webappapi.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;
    private final EventFeatureMapper eventFeatureMapper;
    private final CategoryEventRepo categoryEventRepo;

    public EventServiceImpl(EventRepo eventRepo,
                            EventFeatureMapper eventFeatureMapper,
                            CategoryEventRepo categoryEventRepo)
    {
        this.eventRepo = eventRepo;
        this.eventFeatureMapper = eventFeatureMapper;
        this.categoryEventRepo = categoryEventRepo;
    }

    @Override
    public Event addEvent(EventRequestDTO eventRequestDTO) {
        Event event = this.eventFeatureMapper.fromEventRequestDTO(eventRequestDTO);
        Optional<CategoryEvent> categoryEvent =
                this.categoryEventRepo.findById
                        (eventRequestDTO.getIdCategoryEvent());
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException
                    ("This category's event doesn't exist !");
        event.setCategoryEvent(categoryEvent.get());
        event.setCreatedAt(new Date());
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
    public Event editEvent(String slug, EventRequestDTO eventRequestDTO) {
        Optional<Event> eventDB = this.eventRepo.findBySlug(slug);
        //check if event doesn't exist to throw exception
        if(eventDB.isEmpty())
            throw new RessourceNotFoundException("This Event doesn't exist !");

        //updated event information
//        log.error(" {} ", eventRequestDTO);
        Event event = this.setEventProperties(eventDB.get(), eventRequestDTO);


        return this.eventRepo.saveAndFlush(event);
    }

    private Event setEventProperties(Event event, EventRequestDTO eventRequestDTO){
        if(! eventRequestDTO.getTitre().isEmpty()
                && !eventRequestDTO.getTitre().equals(event.getTitre()))
            event.setTitre(eventRequestDTO.getTitre());

        if(eventRequestDTO.getDuree()!=0
                && !eventRequestDTO.getDuree().equals(event.getDuree()))
            event.setDuree(eventRequestDTO.getDuree());

        if(! eventRequestDTO.getDescription().isEmpty()
                && !eventRequestDTO.getDescription().equals(event.getDescription()))
            event.setDescription(eventRequestDTO.getDescription());

        if(! eventRequestDTO.getDetails().isEmpty()
                && !eventRequestDTO.getDetails().equals(event.getDetails()))
            event.setDetails(eventRequestDTO.getDetails());

        if(! eventRequestDTO.getLieu().isEmpty()
                && !eventRequestDTO.getLieu().equals(event.getLieu()))
            event.setLieu(eventRequestDTO.getLieu());

        if(event.getDateDeb().compareTo(eventRequestDTO.getDateDeb()) != 0)
            event.setDateDeb(eventRequestDTO.getDateDeb());
        if(event.getDateFin().compareTo(eventRequestDTO.getDateFin()) != 0)
            event.setDateFin(eventRequestDTO.getDateFin());
        if(! eventRequestDTO.getIdCategoryEvent().equals(event.getCategoryEvent().getId()))
        {
            Optional<CategoryEvent> categoryEvent = this.categoryEventRepo.findById(eventRequestDTO.getIdCategoryEvent());
            if(categoryEvent.isEmpty())
                throw new RessourceNotFoundException("This category's event doesn't exist !");
            event.setCategoryEvent(categoryEvent.get());
        }

        return event;
    }
}
