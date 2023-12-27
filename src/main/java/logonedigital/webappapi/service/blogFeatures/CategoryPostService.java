package logonedigital.webappapi.service.blogFeatures;

import logonedigital.webappapi.entity.PostCategory;

import java.util.List;

public interface CategoryPostService {
    PostCategory addCategoryPost(PostCategory postCategory);
    List<PostCategory> getCategoriesPost();
    PostCategory getCategoryPost(String slug);
    void deleteCategoryPost(String slug);
    PostCategory editCategoryPost(PostCategory postCategory, String slug);
}
