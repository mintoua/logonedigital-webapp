package logonedigital.webappapi.service.blogFeatures.post;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import logonedigital.webappapi.entity.PostCategory;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.BlogFeaturesMapper;
import logonedigital.webappapi.repository.PostCategoryRepo;
import logonedigital.webappapi.repository.PostRepo;
import logonedigital.webappapi.service.fileManager.FileManager;
import logonedigital.webappapi.utils.Tool;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final BlogFeaturesMapper mapper;
    private final PostCategoryRepo postCategoryRepo;
    private final FileManager fileManager;



    @Override
    public Post addPost(PostReqDTO postReqDTO) throws IOException {

        //Check if current post has been created inside database
        if(this.postRepo.fetchPostByTitle(postReqDTO.getTitle()).isPresent())
            throw new ResourceExistException("This post has been created !");

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
    public Page<Post> getPosts(int offset, int pageSize) {
        return this.postRepo.findAll(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")));
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

    private Post setPostProperties(Post post, PostReqDTO postReqDTO) throws IOException {
        if(!postReqDTO.getTitle().isEmpty()
                && !postReqDTO.getTitle().equals(post.getTitle())){
            post.setTitle(postReqDTO.getTitle());
            post.setSlug(Tool.slugify(postReqDTO.getTitle()));
        }

        if (!postReqDTO.getContent().isEmpty()
                && !postReqDTO.getContent().equals(post.getContent()))
            post.setContent(postReqDTO.getContent());
        Optional<PostCategory> postCategory = this.postCategoryRepo.findBySlug(postReqDTO.getSlugPostCategory());
        if(postCategory.isEmpty())
            throw new RessourceNotFoundException("Post Category's doesn't exist !");
        post.setPostCategory(postCategory.get());
        post.setUpdatedAt(new Date());
        post.setPublished(false);
        //TODO Régler le problème d'upload d'image lors de l'update
        //post.setImgUrl(this.fileManager.uploadFile(postReqDTO.getImageFile()));
        return post;
    }
    @Override
    public Post editPost(PostReqDTO postReqDTO, String slug) throws IOException {
        Optional<Post> post = this.postRepo.findBySlug(slug);
        if (post.isEmpty())
            throw new RessourceNotFoundException("This post doesn't exist!");

       this.setPostProperties(post.get(),postReqDTO);

        return this.postRepo.saveAndFlush(this.setPostProperties(post.get(),postReqDTO));
    }

    @Override
    public Page<Post> getPostsByCategory(String postCategorySlug, int offset, int pageSize) {
        return this.postRepo
                .fetchPostByCategory(postCategorySlug,
                        PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, "title")));
    }

    @Override
    public Page<Post> getPublishedPost(int offset, int pageSize) {

        return this.postRepo.fetchPostByPublished(PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, "title")));
    }
}
