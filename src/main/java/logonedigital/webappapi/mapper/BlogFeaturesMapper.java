package logonedigital.webappapi.mapper;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostCategoryReqDTO;
import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import logonedigital.webappapi.entity.PostCategory;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface BlogFeaturesMapper {
    Post fromPostRequestDTO(PostReqDTO postReqDTO);
    PostCategory fromPostCategoryReqDTO(PostCategoryReqDTO postCategoryReqDTO);
}
