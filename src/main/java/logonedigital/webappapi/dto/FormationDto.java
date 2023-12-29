package logonedigital.webappapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record FormationDto(
        String slug, @NotBlank(message = "Titre is required") String titre,
        @NotBlank(message = "Description is required")String description,
        @NotBlank(message = "Objectifs is required")String objectifs,
        @NotBlank(message = "Contenu is required") String contenu, String imageUrl,
        @DecimalMin(value = "0.0", message = "Prix cannot be negative") Float prix,
        @NotBlank(message = "Categorie is required") String categorie, String brochureUrl

) {
}
