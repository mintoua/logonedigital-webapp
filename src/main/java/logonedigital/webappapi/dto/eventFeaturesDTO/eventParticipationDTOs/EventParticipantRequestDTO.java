package logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import logonedigital.webappapi.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantRequestDTO {
    @NotEmpty(message = "field couldn't be empty")
    @NotBlank(message = "field couldn't be blank")
    private String raisonInscription;
    @NotEmpty(message = "You most select an event")
    private String eventSlug;
}
