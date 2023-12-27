package logonedigital.webappapi.mapper;

import javax.annotation.processing.Generated;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO.EventRequestDTO;
import logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs.EventParticipantRequestDTO;
import logonedigital.webappapi.entity.Event;
import logonedigital.webappapi.entity.EventParticipant;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-27T20:21:43+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class EventFeatureMapperImpl implements EventFeatureMapper {

    @Override
    public Event fromEventRequestDTO(EventRequestDTO eventRequestDTO) {
        if ( eventRequestDTO == null ) {
            return null;
        }

        Event event = new Event();

        event.setTitre( eventRequestDTO.getTitre() );
        event.setDescription( eventRequestDTO.getDescription() );
        event.setDateDeb( eventRequestDTO.getDateDeb() );
        event.setDateFin( eventRequestDTO.getDateFin() );
        event.setDuree( eventRequestDTO.getDuree() );
        event.setNbPlace( eventRequestDTO.getNbPlace() );
        event.setLieu( eventRequestDTO.getLieu() );
        event.setDetails( eventRequestDTO.getDetails() );

        return event;
    }

    @Override
    public EventParticipant fromEventParticipantRequestDRO(EventParticipantRequestDTO eventParticipantRequestDTO) {
        if ( eventParticipantRequestDTO == null ) {
            return null;
        }

        EventParticipant eventParticipant = new EventParticipant();

        eventParticipant.setRaisonInscription( eventParticipantRequestDTO.getRaisonInscription() );

        return eventParticipant;
    }
}
