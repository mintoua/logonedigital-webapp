package logonedigital.webappapi.service.categoryEvent;

import com.github.slugify.Slugify;
import logonedigital.webappapi.entity.CategoryEvent;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.CategoryEventRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryEventServiceImpl implements CategoryEventService {
    private final CategoryEventRepo categoryEventRepo;


    public CategoryEventServiceImpl(CategoryEventRepo categoryEventRepo) {
        this.categoryEventRepo = categoryEventRepo;
    }

    private String slugify(String designation){
        final Slugify slg = Slugify.builder().build();
        return slg.slugify(designation);
    }
    @Override
    public CategoryEvent addCategoryEvent(CategoryEvent categoryEvent) {

        String slug = this.slugify(categoryEvent.getDesignation());
        Optional<CategoryEvent> optionalCategoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(optionalCategoryEvent.isPresent())
            throw new RessourceNotFoundException("this category of event already exist !");
        categoryEvent.setSlug(slug);
        return this.categoryEventRepo.save(categoryEvent);
    }

    @Override
    public List<CategoryEvent> getCategoriesEvent() {
        return this.categoryEventRepo.findAll();
    }

    @Override
    public CategoryEvent getCategoryEvent(String slug) {
        Optional<CategoryEvent> categoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");
        return categoryEvent.get();
    }

    @Override
    public void deleteCategoryEvent(String slug) {
        Optional<CategoryEvent> categoryEvent = this.categoryEventRepo.findBySlug(slug);
        if(categoryEvent.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");
        this.categoryEventRepo.delete(categoryEvent.get());
    }

    @Override
    public CategoryEvent editCategoryEvent(String slug, CategoryEvent categoryEvent) {
        Optional<CategoryEvent> categoryEventOptional = this.categoryEventRepo.findBySlug(slug);

        if(categoryEventOptional.isEmpty())
            throw new RessourceNotFoundException("Resource Not Found !");

        categoryEventOptional.get().setDesignation(categoryEvent.getDesignation());
        categoryEventOptional.get().setSlug(this.slugify(categoryEvent.getDesignation()));
        return this.categoryEventRepo.saveAndFlush(categoryEventOptional.get());
    }


}
