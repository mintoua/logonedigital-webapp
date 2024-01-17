package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import logonedigital.webappapi.dto.accountFeaturesDTO.ActivationDTO;
import logonedigital.webappapi.dto.accountFeaturesDTO.LoginDTO;
import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;
import logonedigital.webappapi.exception.AccountException;
import logonedigital.webappapi.exception.InvalidCredentialException;
import logonedigital.webappapi.exception.ProcessFailureException;
import logonedigital.webappapi.service.accountFeatures.AccountService;
import logonedigital.webappapi.service.accountFeatures.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Operation(summary = "Registration Api", description = "return PostCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User already exist!"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!")
    })
    @PostMapping("account/create")
    public ResponseEntity<String> registration(@RequestBody UserReqDTO userReqDTO){
        this.accountService.registration(userReqDTO);
        return ResponseEntity.status(201)
                .body("Registered successfully!");
    }

    @Operation(summary = "Account activation API", description = "return account activated successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Activation code expired!"),
            @ApiResponse(responseCode = "400", description = "Activation code doesn't exist!"),
            @ApiResponse(responseCode = "202", description = "Account activated successfully!")
    })
    @PutMapping("account/activation")
    public ResponseEntity<String> accountActivation(@RequestBody ActivationDTO activationDTO){
        this.accountService.activateAccount(activationDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Your account activated successfully!");
    }

    @Operation(summary = "Login API", description = "return connected successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Something wrong !"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials !"),
            @ApiResponse(responseCode = "200", description = "Connected successfully !")
    })
    @PostMapping("account/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO){

        Authentication authentication =this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password())
        );

       if(!authentication.isAuthenticated())
           throw new InvalidCredentialException("Invalid credentials");

        return ResponseEntity
                .status(200)
                .body(this.jwtService.generateToken(loginDTO.email()));
    }
}
