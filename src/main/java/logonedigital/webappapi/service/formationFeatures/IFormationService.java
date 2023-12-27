package logonedigital.webappapi.service.formationFeatures;

import logonedigital.webappapi.entity.Formation;

import java.util.List;

public interface IFormationService {
    Formation createFormation(Formation formation);

    Formation getFormationById(Integer id);

    List<Formation> getAllFormations();

    Formation updateFormation(Integer id, Formation updatedFormation);

    void deleteFormation(Integer id);
}
