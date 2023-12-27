package logonedigital.webappapi.service.eventFeatures.eventParticipants;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantRequestDTO;
import logonedigital.webappapi.entity.EventParticipant;

import java.util.List;

public interface EventParticipantService {
    EventParticipant addEventParticipant(EventParticipantRequestDTO eventParticipantRequestDTO);
    List<EventParticipant> getEventParticipants();
    EventParticipant getEventParticipant(Integer id);
    void deleteEventParticipant(Integer id);
    EventParticipant updateEventParticipant(Integer id,EventParticipant eventParticipant);

}
