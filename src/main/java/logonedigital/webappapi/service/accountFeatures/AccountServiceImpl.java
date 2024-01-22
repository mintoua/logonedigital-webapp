package logonedigital.webappapi.service.accountFeatures;


import logonedigital.webappapi.dto.accountFeaturesDTO.*;
import logonedigital.webappapi.entity.Activation;
import logonedigital.webappapi.entity.Role;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.AccountException;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.AccountMapper;
import logonedigital.webappapi.repository.ActivationRepo;
import logonedigital.webappapi.repository.RoleRepo;
import logonedigital.webappapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final ActivationService activationService;
    private final ActivationRepo activationRepo;

    @Override
    public void registration(UserReqDTO userReqDTO) {
        if(!userReqDTO.getPassword().equals(userReqDTO.getPasswordConfirm()))
            throw new AccountException("Password and Password confirm must match");

        Optional<User> userExist = this.userRepo.findByEmail(userReqDTO.getEmail());
        if(userExist.isPresent())
            throw new ResourceExistException("This user has been registered!");

        User user = this.accountMapper.fromUserReqDTO(userReqDTO);
        user.setCreatedAt(Instant.now());
        user.setPassword(this.passwordEncoder.encode(userReqDTO.getPassword()));
        user.setIsActivated(false);
        Role role = this.roleRepo.findByDesignation("ROLE_USER")
                .orElseThrow(()-> new RessourceNotFoundException("ROLE_USER not found!"));
        user.setRoles(Collections.singletonList(role));

        user=this.userRepo.save(user);
        //generate activation code and send it by using the email service
        this.activationService.saveActivationCode(user);
    }

    @Override
    public void addRoleToUser(AddRoleToUserDTO addRoleToUserDTO) throws RessourceNotFoundException {
        User user = this.userRepo
                .findByEmail(addRoleToUserDTO.email())
                .orElseThrow(()-> new RessourceNotFoundException("User not found !"));
        Role role = this.roleRepo
                .findByDesignation(addRoleToUserDTO.roleDesignation())
                .orElseThrow(()-> new RessourceNotFoundException("Role not found !"));
        List<Role> roleList = user.getRoles();
        roleList.add(role);
        user.setRoles(roleList);
        user.setCreatedAt(Instant.now());

        this.userRepo.save(user);
    }

    @Override
    public void updateAccount(UserReqDTO userReqDTO, String email) {

    }

    @Override
    public void disableAccount(String email) {
        User user = this.userRepo.findByEmail(email)
                .orElseThrow(()->new RessourceNotFoundException("User not found!"));

        user.setIsActivated(false);
        user.setUpdatedAt(Instant.now());
        this.userRepo.saveAndFlush(user);
    }

    @Override
    public void activateAccount(ActivationDTO activationDTO) {
        Activation activation = this.activationService.fetchByActivationCode(activationDTO.activationCode());

        //verify if activationCode still available
        if(Instant.now().isAfter(activation.getExpiredAt()))
            throw new AccountException("Activation code expired!");
        User user = activation.getUser();
        user.setIsActivated(true);
        user.setIsBlocked(false);
        activation.setActivatedAt(Instant.now());

        this.userRepo.saveAndFlush(user);
        this.activationRepo.saveAndFlush(activation);
    }

    @Override
    public void login(LoginDTO loginDTO) {

    }

    @Override
    public void editPassword(EditPasswordDTO editPasswordDTO) {
       User user = this.userRepo
               .findByEmail(editPasswordDTO.email())
               .orElseThrow(()-> new RessourceNotFoundException("User not found!"));
       this.activationService.saveActivationCode(user);
    }

    @Override
    public void saveNewPassword(NewPasswordReqDTO newPasswordReqDTO) {
        if (!newPasswordReqDTO.newPassword().equals(newPasswordReqDTO.newPaswordConfirm()))
            throw new AccountException("Password and Password confirm must match");

        User user = this.userRepo
                .findByEmail(newPasswordReqDTO.email())
                .orElseThrow(()-> new RessourceNotFoundException("User not found!"));

        Activation activation = this.activationService.fetchByActivationCode(newPasswordReqDTO.code());

        if(activation.getUser().getEmail().equals(user.getEmail())){
            String newPassword = this.passwordEncoder.encode(newPasswordReqDTO.newPassword());
            user.setPassword(newPassword);
            user.setUpdatedAt(Instant.now());
            this.userRepo.saveAndFlush(user);
        }

    }
}
