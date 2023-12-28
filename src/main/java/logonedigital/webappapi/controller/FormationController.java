package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import logonedigital.webappapi.dto.FormationDto;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.service.formationFeatures.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
@Tag(name = "Formation APIs")
public class FormationController {

    private final FormationService formationService;

    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<FormationDto> createFormation(
            @Valid @RequestBody FormationDto formation
            )
    {
        return ResponseEntity.status(CREATED).body(
                formationService.createFormation(formation)
        );
    }

    @ResponseBody
    @GetMapping("")
    public ResponseEntity<List<Formation>> getAllFormtions(){
        return ResponseEntity.ok(
                formationService.getAllFormations()
        );
    }

    @ResponseBody
    @GetMapping("/{slug}")
    public ResponseEntity<FormationDto> getFormation(
            @PathVariable(name = "slug") String slug){

        return ResponseEntity.ok(formationService.getFormationBySlug(slug));
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping("/edit/{slug}")
    public ResponseEntity<FormationDto> updateFormation(
            @PathVariable(name = "slug") String slug,
            @RequestBody FormationDto formationDto
    ){
        return ResponseEntity.ok(formationService.updateFormation(slug,formationDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id){
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }
}
