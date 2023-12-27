package logonedigital.webappapi.service.eventFeatures.eventParticipants;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantRequestDTO;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.entity.EventParticipant;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.EventFeatureMapper;
import logonedigital.webappapi.repository.EventParticipantRepository;
import logonedigital.webappapi.repository.EventRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventParticipantServiceImpl implements EventParticipantService {

    private final EventParticipantRepository eventParticipantRepo;
    private final EventFeatureMapper eventFeatureMapper;
    private final EventRepo eventRepo;

    public EventParticipantServiceImpl(EventParticipantRepository eventParticipantRepo,
                                       EventFeatureMapper eventFeatureMapper,
                                       EventRepo eventRepo)
    {
        this.eventParticipantRepo = eventParticipantRepo;
        this.eventFeatureMapper = eventFeatureMapper;
        this.eventRepo = eventRepo;
    }


    @Override
    public EventParticipant addEventParticipant(EventParticipantRequestDTO eventParticipantRequestDTO) {
        EventParticipant eventParticipant = this.eventFeatureMapper.fromEventParticipantRequestDRO(eventParticipantRequestDTO);

        eventParticipant.setDateInscription(new Date());
        Optional<Event> event = this.eventRepo.findBySlug(eventParticipantRequestDTO.getEventSlug());
        if(event.isEmpty())
            throw new RessourceNotFoundException("This event dont exist !");
        eventParticipant.setEvents(Collections.singletonList(event.get()));
        return this.eventParticipantRepo.save(eventParticipant);
    }

    @Override
    public List<EventParticipant> getEventParticipants() {
        return this.eventParticipantRepo.findAll();
    }

    @Override
    public EventParticipant getEventParticipant(Integer id) {
        Optional<EventParticipant> eventParticipant = this.eventParticipantRepo.findById(id);
        if (eventParticipant.isEmpty())
            throw new RessourceNotFoundException("This subscription doesn't exist");

        return eventParticipant.get();
    }

    @Override
    public void deleteEventParticipant(Integer id) {
        Optional<EventParticipant> eventParticipant = this.eventParticipantRepo.findById(id);
        if (eventParticipant.isEmpty())
            throw new RessourceNotFoundException("This subscription doesn't exist");

        this.eventParticipantRepo.delete(eventParticipant.get());
    }

    @Override
    public EventParticipant updateEventParticipant(Integer id, EventParticipant eventParticipant) {
        Optional<EventParticipant> oldEventParticipant = this.eventParticipantRepo.findById(id);
        if (oldEventParticipant.isEmpty())
            throw new RessourceNotFoundException("This subscription doesn't exist");



        if(!eventParticipant.getRaisonInscription().isEmpty())
            oldEventParticipant.get().setRaisonInscription(eventParticipant.getRaisonInscription());
        return this.eventParticipantRepo.saveAndFlush(oldEventParticipant.get());
    }
}
