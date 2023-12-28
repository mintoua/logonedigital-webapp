package logonedigital.webappapi.dto;

import jakarta.persistence.Column;

public record FormationDto(
        String slug,
        String titre,
        String description,
        String objectifs,
        String contenu,
        String imageUrl,
        Float prix,
        String categorie,
        String brochureUrl

) {
}
