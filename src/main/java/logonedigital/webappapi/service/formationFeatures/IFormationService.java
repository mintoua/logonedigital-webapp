package logonedigital.webappapi.service.formationFeatures;

import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;

import java.util.List;

public interface IFormationService {
    FormationDto createFormation(FormationDto formation);

    Formation getFormationById(Integer id);
    FormationDto getFormationBySlug(String slug);
    List<Formation> getAllFormations();
    FormationDto updateFormation(String slug, FormationDto updatedFormation);
    void deleteFormation(Integer id);
    List<FormationDto> getAllFormationsCategorie(String categorie);

}
