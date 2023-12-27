package logonedigital.webappapi.mapper;

import logonedigital.webappapi.dto.blogFeaturesDTO.PostReqDTO;
import logonedigital.webappapi.entity.Post;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface BlogFeaturesMapper {
    Post fromPostRequestDTO(PostReqDTO postReqDTO);
}
