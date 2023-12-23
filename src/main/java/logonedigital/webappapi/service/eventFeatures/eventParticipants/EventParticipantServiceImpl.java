package logonedigital.webappapi.service.eventFeatures.eventParticipants;

import logonedigital.webappapi.entity.EventParticipant;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.EventParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventParticipantServiceImpl implements EventParticipantService {

    private final EventParticipantRepository eventParticipantRepo;

    public EventParticipantServiceImpl(EventParticipantRepository eventParticipantRepo) {
        this.eventParticipantRepo = eventParticipantRepo;
    }


    @Override
    public EventParticipant addEventParticipant(EventParticipant eventParticipant) {
        eventParticipant.setDateInscription(new Date());
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
