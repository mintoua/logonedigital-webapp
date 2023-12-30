package logonedigital.webappapi.dto.eventFeaturesDTO.eventDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    private String titre;
    private String description;
    private Date dateDeb;
    private Date dateFin;
    private Double duree;
    private Integer nbPlace;
    private String lieu;
    private String details;
    private Integer idCategoryEvent;
    @NotNull(message = "You must upload an image")
    private MultipartFile imageFile;
}

