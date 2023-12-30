package logonedigital.webappapi.service;

import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.FileData;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.mapper.FormationMapper;
import logonedigital.webappapi.repository.FormationRepository;
import logonedigital.webappapi.service.fileManager.FileManager;
import logonedigital.webappapi.service.formationFeatures.FormationService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



@SpringBootTest
public class FormationServiceTest {

    @Autowired
    private FormationService formationService;

    @MockBean
    private FormationMapper formationMapper;

    @MockBean
    private FileManager fileManager;

    @MockBean
    private FormationRepository formationRepository;

    @Test
    public void testGetFormationsByCategorie() {

        String categorie = "Test Category";
        List<Formation> expectedFormations = Arrays.asList(
                new Formation(1, "Formation 1", "Description 1", 10.99f, categorie),
                new Formation(2, "Formation 2", "Description 2", 19.99f, categorie)
        );

        // Configure mock behavior
        when(formationRepository.findFormationsByCategorie(categorie))
                .thenReturn(expectedFormations);

        // Call the service method
        List<Formation> actualFormations = formationService.getFormationsByCategorie(categorie);

        // Verify results
        assertEquals(expectedFormations, actualFormations);
        // Optionally verify interactions with the repository
    }

    @Test
    public void testCreateFormation() throws IOException {
        // Create mock MultipartFile instances
        MultipartFile imageFile = new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", "some image data".getBytes());
        MultipartFile brochureFile = new MockMultipartFile("brochure.pdf", "brochure.pdf", "application/pdf", "some brochure data".getBytes());

        FormationDto formationDto = new FormationDto(
                "",
                "Test Formation Title",
                "Test Formation Description",
                "Test Objectives",
                "Test Content",
                imageFile,
                19.99f,
                "Test Category",
                brochureFile
        );
        // Mock dependencies
        when(formationRepository.findFormationByTitre(formationDto.titre())).thenReturn(Optional.empty());
        Formation formationEntity = new Formation(); // Expected entity after mapping
        when(formationMapper.formationDtoToEntity(formationDto)).thenReturn(formationEntity);
        FileData imageFileData = new FileData(); // Mock file data
        when(fileManager.uploadFile(formationDto.imageUrl())).thenReturn(imageFileData);
        FileData brochureFileData = new FileData(); // Mock file data
        when(fileManager.uploadFile(formationDto.brochureUrl())).thenReturn(brochureFileData);
        when(formationRepository.save(formationEntity)).thenReturn(formationEntity); // Simulate saving

        Formation createdFormation = formationService.createFormation(formationDto);

        assertEquals(formationEntity, createdFormation);

    }
}