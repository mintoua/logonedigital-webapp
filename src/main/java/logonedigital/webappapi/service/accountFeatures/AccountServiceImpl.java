package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;
import logonedigital.webappapi.entity.Role;
import logonedigital.webappapi.entity.User;
import logonedigital.webappapi.exception.ResourceExistException;
import logonedigital.webappapi.exception.RessourceNotFoundException;
import logonedigital.webappapi.mapper.AccountMapper;
import logonedigital.webappapi.repository.RoleRepo;
import logonedigital.webappapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(UserReqDTO userReqDTO) {

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

        this.userRepo.save(user);
    }

    @Override
    public void updateAccount(UserReqDTO userReqDTO, String email) {

    }

    @Override
    public void lockAccount(String email) {

    }
}
