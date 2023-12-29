package logonedigital.webappapi.service.blogFeatures.post;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.BlogFeaturesMapper;
import logonedigital.webappapi.repository.PostCategoryRepo;
import logonedigital.webappapi.repository.PostRepo;
import logonedigital.webappapi.service.fileManager.FileManager;
import logonedigital.webappapi.utils.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final BlogFeaturesMapper mapper;
    private final PostCategoryRepo postCategoryRepo;
    private final FileManager fileManager;

    public PostServiceImpl(PostRepo postRepo,
                           BlogFeaturesMapper mapper, PostCategoryRepo postCategoryRepo, FileManager fileManager) {
        this.postRepo = postRepo;
        this.mapper = mapper;
        this.postCategoryRepo = postCategoryRepo;
        this.fileManager = fileManager;
    }


    @Override
    public Post addPost(PostReqDTO postReqDTO) throws IOException {
        Post post = this.mapper.fromPostRequestDTO(postReqDTO);
        post.setCreatedAt(new Date());
        post.setSlug(Tool.slugify(post.getTitle()));

        Optional<PostCategory> postCategory = this.postCategoryRepo.findBySlug(postReqDTO.getSlugPostCategory());
        if(postCategory.isEmpty())
            throw new RessourceNotFoundException("Post Category's doesn't exist !");
        post.setPostCategory(postCategory.get());
        post.setImgUrl(this.fileManager.uploadFile(postReqDTO.getImageFile()));
        return this.postRepo.save(post);
    }

    @Override
    public List<Post> getPosts() {
        return this.postRepo.findAll();
    }

    @Override
    public Post getPost(String slug) {
        Optional<Post> post = this.postRepo.findBySlug(slug);

        if (post.isEmpty())
            throw new RessourceNotFoundException("This post doesn't exist !");

        return post.get();
    }

    @Override
    public void deletePost(String slug) {
        Optional<Post> post = this.postRepo.findBySlug(slug);

        if (post.isEmpty())
            throw new RessourceNotFoundException("This post doesn't exist !");

        this.postRepo.delete(post.get());
    }

    @Override
    public Post editPost(PostReqDTO postReqDTO, String slug) {
        Optional<Post> post = this.postRepo.findBySlug(slug);
        if (post.isEmpty())
            throw new RessourceNotFoundException("This post doesn't exist!");
        Post postDB = post.get();
        //TODO refactoriser ce controller avec une fonction private
        if(!postReqDTO.getTitle().isEmpty()
                && !postReqDTO.getTitle().equals(postDB.getTitle())){
            postDB.setTitle(postReqDTO.getTitle());
            postDB.setSlug(Tool.slugify(postReqDTO.getTitle()));
        }

        if (!postReqDTO.getContent().isEmpty()
                && !postReqDTO.getContent().equals(postDB.getContent()))
            postDB.setContent(postReqDTO.getContent());
        //TODO ajouter la condition pour vérifier si la catégorie n'est pas vie et n'est pas identique à celle existante
        Optional<PostCategory> postCategory = this.postCategoryRepo.findBySlug(postReqDTO.getSlugPostCategory());
        if(postCategory.isEmpty())
            throw new RessourceNotFoundException("Post Category's doesn't exist !");
        postDB.setPostCategory(postCategory.get());

        postDB.setUpdatedAt(new Date());
        return this.postRepo.saveAndFlush(postDB);
    }
}
