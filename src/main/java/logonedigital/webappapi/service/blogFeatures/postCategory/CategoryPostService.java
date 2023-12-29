package logonedigital.webappapi.service.blogFeatures.postCategory;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostCategoryReqDTO;
import logonedigital.webappapi.entity.PostCategory;
import org.springframework.data.domain.Page;



public interface CategoryPostService {
    PostCategory addCategoryPost(PostCategoryReqDTO postCategoryReqDTO);
    Page<PostCategory> getCategoriesPost(int offset, int pageSize);
    PostCategory getCategoryPost(String slug);
    void deleteCategoryPost(String slug);
    PostCategory editCategoryPost(PostCategory postCategory, String slug);
}
