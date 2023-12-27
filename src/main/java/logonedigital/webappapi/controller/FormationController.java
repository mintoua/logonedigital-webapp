package logonedigital.webappapi.controller;

import jakarta.validation.Valid;
import logonedigital.webappapi.entity.Formation;
import logonedigital.webappapi.service.formationFeatures.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    @PostMapping("/new-formation")
    public ResponseEntity<Formation> createFormation(
            @Valid @RequestBody Formation formation
            )
    {
        return ResponseEntity.status(CREATED).body(
                formationService.createFormation(formation)
        );
    }

    @GetMapping("")
    public ResponseEntity<List<Formation>> getAllFormtions(){
        return ResponseEntity.ok(
                formationService.getAllFormations()
        );
    }
}
