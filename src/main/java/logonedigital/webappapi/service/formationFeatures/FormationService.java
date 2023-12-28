package logonedigital.webappapi.service.formationFeatures;

import jakarta.persistence.EntityNotFoundException;
import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.repository.FormationRepository;
import logonedigital.webappapi.utils.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormationService implements IFormationService {

    private final FormationRepository formationRepository;

    @Override
    public FormationDto createFormation(FormationDto formationDto) {
    Formation formation =  new Formation();
    formation.setSlug(Tool.slugify(formationDto.titre()));
    fromDto(formationDto, formation);
    return fromEntity(formationRepository.save(formation));
    }
    private void fromDto(FormationDto formationDto, Formation formation) {
        formation.setTitre(formationDto.titre());
        formation.setDescription(formationDto.description());
        formation.setObjectifs(formationDto.objectifs());
        formation.setContenu(formationDto.contenu());
        formation.setImageUrl(formationDto.imageUrl());
        formation.setPrix(formationDto.prix());
        formation.setCategorie(formationDto.categorie());
        formation.setBrochureFile(formationDto.brochureUrl());
    }

    private FormationDto fromEntity(Formation formation){
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
