package logonedigital.webappapi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Event implements Serializable {
    //TODO réglé le problème de référence circulaire dans cette entité
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
    private Integer nbPlaceRestante;
    private String lieu;
    @Column(columnDefinition = "TEXT")
    private String details;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id_event_category_id")
    @JsonIgnoreProperties("events")
    private EventCategory eventCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("events")
    private EventParticipant eventParticipant;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private FileData imgUrl;

}
