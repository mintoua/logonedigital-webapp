package logonedigital.webappapi.service.eventFeatures.event;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.entity.EventCategory;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.EventFeatureMapper;
import logonedigital.webappapi.repository.CategoryEventRepo;
import logonedigital.webappapi.repository.EventRepo;
import logonedigital.webappapi.service.fileManager.FileManager;
import logonedigital.webappapi.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;
    private final EventFeatureMapper eventFeatureMapper;
    private final CategoryEventRepo categoryEventRepo;
    private final FileManager fileManager;

    public EventServiceImpl(EventRepo eventRepo,
                            EventFeatureMapper eventFeatureMapper,
                            CategoryEventRepo categoryEventRepo,
                            FileManager fileManager)
    {
        this.eventRepo = eventRepo;
        this.eventFeatureMapper = eventFeatureMapper;
        this.categoryEventRepo = categoryEventRepo;
        this.fileManager = fileManager;
    }

    @Override
    public Event addEvent(EventRequestDTO eventRequestDTO) throws IOException {

        if(this.eventRepo.fetchEventByTitle(eventRequestDTO.getTitre()).isPresent())
            throw new ResourceExistException("This event already was created!");

        Event event = this.eventFeatureMapper.fromEventRequestDTO(eventRequestDTO);
        Optional<EventCategory> categoryEvent = this.categoryEventRepo.findById(eventRequestDTO.getIdCategoryEvent());
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException("This category's event doesn't exist !");
        event.setEventCategory(categoryEvent.get());
        event.setCreatedAt(new Date());
        event.setSlug(Tool.slugify(event.getTitre()));
        event.setNbPlaceRestante(event.getNbPlace());
        event.setPublished(false);
        event.setImgUrl(this.fileManager.uploadFile(eventRequestDTO.getImageFile()));

        return this.eventRepo.save(event);
    }



    @Override
    public Page<Event> getEvents(int offset, int pageSize) {
        return this.eventRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
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
    public Event editEvent(String slug, EventRequestDTO eventRequestDTO) throws IOException{
        Optional<Event> eventDB = this.eventRepo.findBySlug(slug);
        //check if event doesn't exist to throw exception
        if(eventDB.isEmpty())
            throw new RessourceNotFoundException("This Event doesn't exist !");

        //updated event information

        Event event = this.setEventProperties(eventDB.get(), eventRequestDTO);
        event.setSlug(Tool.slugify(event.getTitre()));
        event.setImgUrl(this.fileManager.uploadFile(eventRequestDTO.getImageFile()));

        return this.eventRepo.saveAndFlush(event);
    }

    @Override
    public Page<Event> getEventsByEventCategory(String slug, int offset, int pageSize) {
        return this.eventRepo.fetchEventsByEventCategory(slug,
                PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @Override
    public Page<Event> getPublishedEvents(int offset, int pageSize) {
        return this.eventRepo.fectchPublishedEvents(PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.DESC, "createdAt")));
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
        if(! eventRequestDTO.getIdCategoryEvent().equals(event.getEventCategory().getId()))
        {
            Optional<EventCategory> categoryEvent = this.categoryEventRepo.findById(eventRequestDTO.getIdCategoryEvent());
            if(categoryEvent.isEmpty())
                throw new RessourceNotFoundException("This category's event doesn't exist !");
            event.setEventCategory(categoryEvent.get());
        }

        return event;
    }
}
