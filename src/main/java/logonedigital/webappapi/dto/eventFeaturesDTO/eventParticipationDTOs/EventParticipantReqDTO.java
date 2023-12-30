package logonedigital.webappapi.dto.eventFeaturesDTO.eventParticipationDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantReqDTO {
    @NotEmpty(message = "field couldn't be empty")
    @NotBlank(message = "field couldn't be blank")
    private String raisonInscription;
    @NotEmpty(message = "You most select an event")
    private String eventSlug;
}
