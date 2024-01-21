package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.*;


public interface AccountService {
    void registration(UserReqDTO userReqDTO);
    void addRoleToUser(AddRoleToUserDTO addRoleToUserDTO);
    void updateAccount(UserReqDTO userReqDTO, String email);
    void lockAccount(String email);

    void activateAccount(ActivationDTO activationDTO);

    void login(LoginDTO loginDTO);

    void editPassword(EditPasswordDTO editPasswordDTO);

    void saveNewPassword(NewPasswordReqDTO newPasswordReqDTO);
}
