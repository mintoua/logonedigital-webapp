package logonedigital.webappapi.repository;

import jakarta.validation.constraints.NotBlank;
import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationRepository
        extends JpaRepository<Formation, Integer> {
    @Query("select distinct f.categorie from Formation f")
    List<String> findDistinctCategorie();
    Optional<Formation> findBySlug(String slug);
    @Query("select f from Formation f where f.categorie = :categorie")
    List<Formation> findFormationsByCategorie(String categorie);

    @Query("select f from Formation f where f.titre = :titre")
    Optional<Formation> findFormationByTitre(
            @NotBlank(message = "Titre is required") String titre);
}
