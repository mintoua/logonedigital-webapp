package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "formations")
public class Formation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Slug is required")
    private String slug;

    @NotBlank(message = "Titre is required")
    @Column(unique = true)
    private String titre;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Objectifs is required")
    @Column(columnDefinition = "TEXT")
    private String objectifs;

    @NotBlank(message = "Contenu is required")
    @Column(columnDefinition = "TEXT")
    private String contenu;

    private String imageUrl;

    @DecimalMin(value = "0.0", message = "Prix cannot be negative")
    private Float prix;

    @NotBlank(message = "Categorie is required")
    private String categorie;

    private String brochureFile;

}
