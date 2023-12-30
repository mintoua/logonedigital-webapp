package logonedigital.webappapi.service.eventFeatures.eventCategory;

import logonedigital.webappapi.dto.eventFeaturesDTO.eventCategoryReqDTO.EventCategoryDTO;
import logonedigital.webappapi.entity.EventCategory;

import java.util.List;

public interface EventCategoryService {
     EventCategory addCategoryEvent(EventCategoryDTO eventCategoryDTO);
     List<EventCategory> getCategoriesEvent();
     EventCategory getCategoryEvent(String slug);
     void deleteCategoryEvent(String slug);
     EventCategory editCategoryEvent(String slug, EventCategory eventCategory);
}
