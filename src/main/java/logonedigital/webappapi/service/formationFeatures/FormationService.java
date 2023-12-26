package logonedigital.webappapi.service.formationFeatures;

import jakarta.persistence.EntityNotFoundException;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.repository.FormationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormationService implements IFormationService {

    private final FormationRepository formationRepository;

    @Override
    public Formation createFormation(Formation formation) {
        // Additional logic can be added before saving to the repository
        return formationRepository.save(formation);
    }

    @Override
    public Formation getFormationById(Integer id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public Formation updateFormation(Integer id, Formation updatedFormation) {
        Formation existingFormation = getFormationById(id);

        // Update the existingFormation with values from updatedFormation
        existingFormation.setTitre(updatedFormation.getTitre());
        existingFormation.setDescription(updatedFormation.getDescription());
        existingFormation.setObjectifs(updatedFormation.getObjectifs());
        existingFormation.setContenu(updatedFormation.getContenu());
        existingFormation.setImageUrl(updatedFormation.getImageUrl());
        existingFormation.setPrix(updatedFormation.getPrix());

        // Additional logic can be added before saving to the repository

        return formationRepository.save(existingFormation);
    }

    @Override
    public void deleteFormation(Integer id) {
        Formation formationToDelete = getFormationById(id);
        formationRepository.delete(formationToDelete);
    }
}
