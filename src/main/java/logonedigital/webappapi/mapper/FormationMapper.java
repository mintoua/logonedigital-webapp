package logonedigital.webappapi.mapper;

import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.FileData;
import logonedigital.webappapi.entity.Formation;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

@Mapper(componentModel = "spring") // For Spring integration
public interface FormationMapper {
    Formation formationDtoToEntity(FormationDto dto);

}