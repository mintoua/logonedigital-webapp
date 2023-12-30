package logonedigital.webappapi.service.eventFeatures.eventParticipants;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantReqDTO;
import logonedigital.webappapi.entity.EventParticipant;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventParticipantService {
    EventParticipant addEventParticipant(EventParticipantReqDTO eventParticipantReqDTO);
    Page<EventParticipant> getEventParticipants(int offset, int pageSize);
    EventParticipant getEventParticipant(Integer id);
    void deleteEventParticipant(Integer id);
    EventParticipant updateEventParticipant(Integer id,EventParticipant eventParticipant);

}
