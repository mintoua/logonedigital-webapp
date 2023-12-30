package logonedigital.webappapi.service.formationFeatures;

import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;

import java.io.IOException;
import java.util.List;

public interface IFormationService {
    Formation createFormation(FormationDto formation) throws IOException;

    Formation getFormationById(Integer id);
    Formation getFormationBySlug(String slug);
    List<Formation> getAllFormations();
    Formation updateFormation(String slug, FormationDto updatedFormation) throws IOException;
    void deleteFormation(String id);
    List<Formation> getFormationsByCategorie(String categorie);

}
