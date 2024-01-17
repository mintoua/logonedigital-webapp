package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;
import logonedigital.webappapi.service.accountFeatures.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Registration Api", description = "return PostCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!")
    })
    @PostMapping("users/")
    public ResponseEntity<String> registration(@RequestBody UserReqDTO userReqDTO){
        this.accountService.registration(userReqDTO);
        return ResponseEntity.status(201)
                .body("Registered successfully!");
    }
}
