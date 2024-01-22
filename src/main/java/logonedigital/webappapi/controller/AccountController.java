package logonedigital.webappapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import logonedigital.webappapi.dto.accountFeaturesDTO.*;
import logonedigital.webappapi.exception.AccountException;
import logonedigital.webappapi.exception.InvalidCredentialException;
import logonedigital.webappapi.exception.ProcessFailureException;
import logonedigital.webappapi.service.accountFeatures.AccountService;
import logonedigital.webappapi.service.accountFeatures.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Registration Api", description = "return Message")
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
    //Todo se rassurer que l'utilisateur est authentifier pour ajouter un role
    @Operation(summary = "Add Role To User Api", description = "return Message", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "User already exist!"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "201", description = "Successfully saved!")
    })
    @PreAuthorize("hasRole('ROLE_DIRECTION')")
    @PutMapping("/account/add-role-to-user")
    public ResponseEntity<String> addRoleToUser(@RequestBody AddRoleToUserDTO addRoleToUserDTO){
        this.accountService.addRoleToUser(addRoleToUserDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Your account activated successfully!");
    }

    @Operation(summary = "Reset Password API", description = "return Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Something wrong !"),
            @ApiResponse(responseCode = "200", description = "Code was send you by email!")
    })
    @PutMapping("account/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody EditPasswordDTO editPasswordDTO){
        this.accountService.editPassword(editPasswordDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Code was send you by email!");
    }

    @Operation(summary = "Update Password API", description = "return Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Something wrong !"),
            @ApiResponse(responseCode = "200", description = "Password edit successfully!")
    })
    @PutMapping("account/new-password")
    public ResponseEntity<String> newPassword(@RequestBody NewPasswordReqDTO newPasswordReqDTO){
        this.accountService.saveNewPassword(newPasswordReqDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Password edit successfully!");
    }

    @Operation(summary = "Login API", description = "return connected successfully")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Something wrong !"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials !"),
            @ApiResponse(responseCode = "200", description = "Connected successfully !")
    })
    @PostMapping("account/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO){
        //TODO vérifier si le compte de l'utilisateur est actif avand d'autoriser la connexion

        Authentication authentication =this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password())
        );

       if(!authentication.isAuthenticated())
           throw new InvalidCredentialException("Invalid credentials");

        return ResponseEntity
                .status(200)
                .body(this.jwtService.generateToken(loginDTO.email()));
    }

    @PostMapping("account/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> refreshTokenReq){
        return ResponseEntity
                .status(200)
                .body(this.jwtService.refreshToken(refreshTokenReq));
    }

    @Operation(summary = "Logout API", description = "Return Message", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Please provide a correct token !"),
            @ApiResponse(responseCode = "200", description = "Successfully logout!")
    })
    @PostMapping("account/logout")
    public ResponseEntity<String> logout(){
        this.jwtService.logout();
        return ResponseEntity
                .status(200)
                .body("Logout successfully");
    }

    @Operation(summary = "Disable Account APU", description = "Return Message", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found!"),
            @ApiResponse(responseCode = "200", description = "Account Disabled Successfully!")
    })
    @PostMapping("account/disable/{email}")
    public ResponseEntity<String> disableAccount(@PathVariable(name = "email") String email){
        this.accountService.disableAccount(email);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Account disabled successfully !");
    }


}
