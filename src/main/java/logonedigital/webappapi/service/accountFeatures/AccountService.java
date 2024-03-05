package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.*;


public interface AccountService {
    void registration(UserReqDTO userReqDTO);
    void addRoleToUser(AddRoleToUserDTO addRoleToUserDTO);
    void updateAccount(EditUserDTO editUserDTO, String email);
    void disableAccount(String email);


    void activateAccount(ActivationDTO activationDTO);
   void resendActivationCode(String email);

    void login(LoginDTO loginDTO);
    void logout();

    void editPassword(EditPasswordDTO editPasswordDTO);

    void saveNewPassword(NewPasswordReqDTO newPasswordReqDTO);
}
