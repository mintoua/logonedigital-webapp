package logonedigital.webappapi.service;

import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.repository.FormationRepository;
import logonedigital.webappapi.service.formationFeatures.FormationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormationServiceTest {

    @Mock
    private FormationRepository formationRepository;

    private FormationService formationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        formationService = new FormationService(formationRepository);
    }

/*    @Test
    public void createFormationShouldReturnFormation() {
        // Create a FormationDto with test data
        FormationDto formationDto = new FormationDto(
                "",
                "FullStack MEAN",
                "Formation Fullstack JS MongoDBExpressAngularNodeJs",
                "Devenir Dev Fullstack JS",
                "M E A N",
                "/image",
                1500.0f,
                "Web",
                "/borchure"
        );

        // Call the createFormation method
        FormationDto createdFormationDto = formationService.createFormation(formationDto);

        // Verify that the repository's save method was called with the correct Formation object
        ArgumentCaptor<Formation> formationCaptor = ArgumentCaptor.forClass(Formation.class);
        Mockito.verify(formationRepository).save(formationCaptor.capture());

        // Assert that the captured Formation object matches the expected values
        Formation savedFormation = formationCaptor.getValue();
        assertEquals(formationDto.slug(), savedFormation.getSlug());
        assertEquals(formationDto.titre(), savedFormation.getTitre());
        // ... (add assertions for other fields as needed)
    }*/
}

