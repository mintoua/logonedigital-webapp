package logonedigital.webappapi.mapper;


import logonedigital.webappapi.dto.eventFeaturesDTO.eventCategoryReqDTO.EventCategoryDTO;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantReqDTO;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.entity.EventCategory;
import logonedigital.webappapi.entity.EventParticipant;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface EventFeatureMapper {
    Event fromEventRequestDTO(EventRequestDTO eventRequestDTO);
    EventParticipant fromEventParticipantRequestDRO(EventParticipantReqDTO eventParticipantReqDTO);
    EventCategory fromEventCategoryDTO(EventCategoryDTO eventCategoryDTO);

}
