package logonedigital.webappapi.service.blogFeatures.postCategory;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostCategoryReqDTO;
import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.BlogFeaturesMapper;
import logonedigital.webappapi.repository.PostCategoryRepo;
import logonedigital.webappapi.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryPostServiceImpl implements CategoryPostService {
    private final PostCategoryRepo postCategoryRepo;
    private final BlogFeaturesMapper blogFeaturesMapper;

    public CategoryPostServiceImpl(PostCategoryRepo postCategoryRepo,
                                   BlogFeaturesMapper blogFeaturesMapper)
    {
        this.postCategoryRepo = postCategoryRepo;
        this.blogFeaturesMapper = blogFeaturesMapper;
    }


    @Override
    public PostCategory addCategoryPost(PostCategoryReqDTO postCategoryReqDTO) {
        PostCategory postCategory = this.blogFeaturesMapper.fromPostCategoryReqDTO(postCategoryReqDTO);
        postCategory.setSlug(Tool.slugify(postCategory.getTitle()));
        if(postCategoryRepo.fetchCategoryPostByTitle(postCategory.getTitle()).isPresent())
            throw new ResourceExistException("This post category's already exist !");
        return this.postCategoryRepo.save(postCategory);
    }

    @Override
    public Page<PostCategory> getCategoriesPost(int offset, int pageSize) {
        return this.postCategoryRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC,"title")));
    }

    @Override
    public PostCategory getCategoryPost(String slug) {
        Optional<PostCategory> categoryPost = this.postCategoryRepo.findBySlug(slug);
        if(categoryPost.isEmpty())
            throw new RessourceNotFoundException("This  post category's doesn't exist !");

        return categoryPost.get();
    }

    @Override
    public void deleteCategoryPost(String slug) {
        Optional<PostCategory> categoryPost = this.postCategoryRepo.findBySlug(slug);
        if(categoryPost.isEmpty())
            throw new RessourceNotFoundException("This  post category's doesn't exist !");
        this.postCategoryRepo.delete(categoryPost.get());
    }

    @Override
    public PostCategory editCategoryPost(PostCategory postCategory, String slug) {
        Optional<PostCategory> categoryPostDB = this.postCategoryRepo.findBySlug(slug);

        if(categoryPostDB.isEmpty())
            throw new RessourceNotFoundException("This Category's post doesn't exist !");

        if(!categoryPostDB.get().getTitle().isEmpty()
                && !categoryPostDB.get().getTitle().equals(postCategory.getTitle()))
            categoryPostDB.get().setTitle(postCategory.getTitle());


        return this.postCategoryRepo.saveAndFlush(categoryPostDB.get());
    }
}
