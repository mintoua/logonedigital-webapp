package logonedigital.webappapi.service.formationFeatures;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.mapper.FormationMapper;
import logonedigital.webappapi.repository.FormationRepository;
import logonedigital.webappapi.service.fileManager.FileManager;
import logonedigital.webappapi.utils.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FormationService implements IFormationService {

    private final FormationRepository formationRepository;
    private final FormationMapper formationMapper;
    private final FileManager fileManager;

    @Override
    public Formation createFormation(FormationDto formationDto) throws IOException {
    if (formationRepository.findFormationByTitre(formationDto.titre()).isPresent())
    {
        throw new ResourceExistException("Une formation avec ce titre existe déjà!!!");
    }
    Formation formation =  formationMapper.formationDtoToEntity(formationDto);
    formation.setSlug(Tool.slugify(formation.getTitre()));
    formation.setImageUrl(fileManager.uploadFile(formationDto.imageUrl()));
    formation.setBrochureFile(fileManager.uploadFile(formationDto.brochureUrl()));
    return formationRepository.save(formation);
    }



    @Override
    public Formation getFormationById(Integer id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
    }

    @Override
    public Formation getFormationBySlug(String slug) {
        Optional<Formation> isFormation = formationRepository.findBySlug(slug);
        if(isFormation.isEmpty()){
            throw new EntityNotFoundException("Formation not found with the slug: " + slug);
        }

        return isFormation.get();
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }



    @Override
    public Formation updateFormation(String slug, FormationDto updatedFormation) throws IOException {
        Optional<Formation> isFormation = formationRepository.findBySlug(slug);
        if(isFormation.isPresent()){
            Formation existingFormation =  isFormation.get();
            return this.createFormation(updatedFormation);
        }
        else{
            throw new EntityNotFoundException("Formation not found with slug: " + slug);
        }
    }

    @Override
    public void deleteFormation(String slug) {
        Optional<Formation> isFormation = formationRepository.findBySlug(slug);
        if(isFormation.isEmpty()){
            throw new EntityNotFoundException("Formation not found with slug: " + slug);
        }
        formationRepository.delete(isFormation.get());
    }

    @Override
    public List<Formation> getFormationsByCategorie(String categorie) {
        return formationRepository.findFormationsByCategorie(categorie);
    }

}
