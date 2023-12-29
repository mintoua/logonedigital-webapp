package logonedigital.webappapi.service.formationFeatures;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.repository.FormationRepository;
import logonedigital.webappapi.utils.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FormationService implements IFormationService {

    private final FormationRepository formationRepository;
    private final Validator validator;

    @Override
    public FormationDto createFormation(FormationDto formationDto) {
        Set<ConstraintViolation<FormationDto>> violations =
                validator.validate(formationDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    Formation formation =  new Formation();
    fromDto(formationDto, formation);
    return fromEntity(formationRepository.save(formation));
    }
    public void fromDto(FormationDto formationDto, Formation formation) {
        formation.setSlug(Tool.slugify(formationDto.titre()));
        formation.setTitre(formationDto.titre());
        formation.setDescription(formationDto.description());
        formation.setObjectifs(formationDto.objectifs());
        formation.setContenu(formationDto.contenu());
        formation.setImageUrl(formationDto.imageUrl());
        formation.setPrix(formationDto.prix());
        formation.setCategorie(formationDto.categorie());
        formation.setBrochureFile(formationDto.brochureUrl());
    }

    public FormationDto fromEntity(Formation formation){
        return new FormationDto(formation.getSlug(), formation.getTitre(),
                formation.getDescription(), formation.getObjectifs(),
                formation.getContenu(), formation.getImageUrl(),
                formation.getPrix(), formation.getCategorie(),
                formation.getBrochureFile());
    }
    @Override
    public Formation getFormationById(Integer id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id: " + id));
    }

    @Override
    public FormationDto getFormationBySlug(String slug) {
        Optional<Formation> isFormation = formationRepository.findBySlug(slug);
        if(isFormation.isEmpty()){
            throw new EntityNotFoundException("Formation not found with slug: " + slug);
        }
        Formation formation = isFormation.get();
        return fromEntity(formation);
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public FormationDto updateFormation(String slug, FormationDto updatedFormation) {
        Optional<Formation> isFormation = formationRepository.findBySlug(slug);
        if(isFormation.isEmpty()){
            throw new EntityNotFoundException("Formation not found with slug: " + slug);
        }
        Formation existingFormation =  isFormation.get();
        fromDto(updatedFormation, existingFormation);

        return fromEntity(formationRepository.save(existingFormation));
    }

    @Override
    public void deleteFormation(Integer id) {
        Formation formationToDelete = getFormationById(id);
        formationRepository.delete(formationToDelete);
    }

    @Override
    public List<FormationDto> getAllFormationsCategorie(String categorie) {
        return formationRepository.findFormationsByCategorie(categorie)
                .stream().map(
                        this::fromEntity
                ).toList();
    }


}
