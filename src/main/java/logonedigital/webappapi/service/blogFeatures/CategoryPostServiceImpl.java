package logonedigital.webappapi.service.blogFeatures;

import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.repository.CategoryPostRepo;
import logonedigital.webappapi.utils.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryPostServiceImpl implements CategoryPostService {
    private final CategoryPostRepo categoryPostRepo;

    public CategoryPostServiceImpl(CategoryPostRepo categoryPostRepo) {
        this.categoryPostRepo = categoryPostRepo;
    }


    @Override
    public PostCategory addCategoryPost(PostCategory postCategory) {

        postCategory.setSlug(Tool.slugify(postCategory.getTitle()));
        return this.categoryPostRepo.save(postCategory);
    }

    @Override
    public List<PostCategory> getCategoriesPost() {
        return this.categoryPostRepo.findAll();
    }

    @Override
    public PostCategory getCategoryPost(String slug) {
        Optional<PostCategory> categoryPost = this.categoryPostRepo.findBySlug(slug);
        if(categoryPost.isEmpty())
            throw new RessourceNotFoundException("This  post category's doesn't exist !");

        return categoryPost.get();
    }

    @Override
    public void deleteCategoryPost(String slug) {
        Optional<PostCategory> categoryPost = this.categoryPostRepo.findBySlug(slug);
        if(categoryPost.isEmpty())
            throw new RessourceNotFoundException("This  post category's doesn't exist !");
        this.categoryPostRepo.delete(categoryPost.get());
    }

    @Override
    public PostCategory editCategoryPost(PostCategory postCategory, String slug) {
        Optional<PostCategory> categoryPostDB = this.categoryPostRepo.findBySlug(slug);

        if(categoryPostDB.isEmpty())
            throw new RessourceNotFoundException("This Category's post doesn't exist !");

        if(!categoryPostDB.get().getTitle().isEmpty()
                && !categoryPostDB.get().getTitle().equals(postCategory.getTitle()))
            categoryPostDB.get().setTitle(postCategory.getTitle());


        return this.categoryPostRepo.saveAndFlush(categoryPostDB.get());
    }
}
