package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.ActivationDTO;
import logonedigital.webappapi.dto.accountFeaturesDTO.LoginDTO;
import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;


public interface AccountService {
    void registration(UserReqDTO userReqDTO);
    void updateAccount(UserReqDTO userReqDTO, String email);
    void lockAccount(String email);

    void activateAccount(ActivationDTO activationDTO);

    void login(LoginDTO loginDTO);
}
