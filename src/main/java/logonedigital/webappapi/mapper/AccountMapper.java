package logonedigital.webappapi.mapper;

import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;
import logonedigital.webappapi.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface AccountMapper {
    User fromUserReqDTO(UserReqDTO userReqDTO);
}
