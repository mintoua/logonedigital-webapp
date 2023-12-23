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
    @NotEmpty (message = "Title couldn't be empty")
    @NotBlank(message = "Title couldn't be blank")
    private String titre;
    @NotEmpty (message = "Description couldn't be empty")
    @NotBlank(message = "Description couldn't be blank")
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull(message = "Field couldn't be empty")
    @Temporal(TemporalType.DATE)
    private Date dateDeb;
    @NotNull(message = "Field couldn't be empty")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @NotNull(message = "Field couldn't be empty")
    private Double duree;
    @NotNull(message = "Field couldn't be empty")
    private Integer nbPlace;
    @NotEmpty (message = "Field couldn't be empty")
    @NotBlank(message = "Field couldn't be blank")
    private String lieu;
    private String details;

}
