package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Event implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String slug;
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateDeb;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    private Double duree;
    private Integer nbPlace;
    private String lieu;
    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne
    private CategoryEvent categoryEvent;
    @ManyToOne
    private EventParticipant eventParticipant;

}
