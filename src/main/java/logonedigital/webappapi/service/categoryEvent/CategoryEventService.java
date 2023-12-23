package logonedigital.webappapi.service.categoryEvent;

import logonedigital.webappapi.entity.CategoryEvent;

import java.util.List;

public interface CategoryEventService {
    public CategoryEvent addCategoryEvent(CategoryEvent categoryEvent);
    public List<CategoryEvent> getCategoriesEvent();
    public CategoryEvent getCategoryEvent(String slug);
    public void deleteCategoryEvent(String slug);
    public CategoryEvent editCategoryEvent(String slug, CategoryEvent categoryEvent);
}
