package logonedigital.webappapi.service.eventFeatures.eventCategory;


import logonedigital.webappapi.dto.eventFeaturesDTO.eventCategoryReqDTO.EventCategoryDTO;
import logonedigital.webappapi.entity.EventCategory;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.EventFeatureMapper;
import logonedigital.webappapi.repository.CategoryEventRepo;
import logonedigital.webappapi.utils.Tool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventCategoryServiceImpl implements EventCategoryService {
    private final CategoryEventRepo categoryEventRepo;
    private final EventFeatureMapper eventFeatureMapper;


    @Override
    public EventCategory addCategoryEvent(EventCategoryDTO eventCategoryDTO ) {

        EventCategory eventCategory = this.eventFeatureMapper.fromEventCategoryDTO(eventCategoryDTO);

        String slug = Tool.slugify(eventCategory.getDesignation());
        Optional<EventCategory> optionalCategoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(optionalCategoryEvent.isPresent())
            throw new ResourceExistException("this category of event already exist !");
        eventCategory.setSlug(slug);
        return this.categoryEventRepo.save(eventCategory);
    }

    @Override
    public List<EventCategory> getCategoriesEvent() {
        return this.categoryEventRepo.findAll();
    }

    @Override
    public EventCategory getCategoryEvent(String slug) {
        Optional<EventCategory> categoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");
        return categoryEvent.get();
    }

    @Override
    public void deleteCategoryEvent(String slug) {
        Optional<EventCategory> categoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");
        this.categoryEventRepo.delete(categoryEvent.get());
    }

    @Override
    public EventCategory editCategoryEvent(String slug, EventCategory eventCategory) {
        Optional<EventCategory> categoryEventOptional = this.categoryEventRepo.findBySlug(slug);

        if(categoryEventOptional.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");

        categoryEventOptional.get().setDesignation(eventCategory.getDesignation());
        categoryEventOptional.get().setSlug(Tool.slugify(eventCategory.getDesignation()));
        return this.categoryEventRepo.saveAndFlush(categoryEventOptional.get());
    }


}
