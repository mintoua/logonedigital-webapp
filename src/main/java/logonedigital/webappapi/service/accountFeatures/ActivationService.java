package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.ActivationDTO;
import logonedigital.webappapi.entity.Activation;
import logonedigital.webappapi.entity.User;

public interface ActivationService {
    void saveActivationCode(User user);
    Activation fetchByActivationCode(String code);
    void saveActivationCodeWhenResetPassword(User user);

}
