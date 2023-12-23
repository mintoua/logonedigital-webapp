package logonedigital.webappapi.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date dateInscription;
    @NotEmpty(message = "field couldn't be empty")
    @NotBlank(message = "field couldn't be blank")
    @Column(columnDefinition = "TEXT")
    private String raisonInscription;

}
