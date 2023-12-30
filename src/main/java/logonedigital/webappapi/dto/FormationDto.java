package logonedigital.webappapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public record FormationDto(
        String slug, @NotBlank(message = "Titre is required") String titre,
        @NotBlank(message = "Description is required")String description,
        @NotBlank(message = "Objectifs is required")String objectifs,
        @NotBlank(message = "Contenu is required") String contenu,
        @Schema(name = "Formation Image", example ="image.jpg/jpeg/png")
        @NotNull(message = "You must upload an image")
        MultipartFile imageUrl,
        @DecimalMin(value = "0.0", message = "Prix cannot be negative") Float prix,
        @NotBlank(message = "Categorie is required") String categorie,
        @Schema(name = "Formation Brochure", example ="brochure.pdf")
        @NotNull(message = "You must upload an pdf")
        MultipartFile brochureUrl
) {

}
