package logonedigital.webappapi.service.accountFeatures;

import logonedigital.webappapi.dto.accountFeaturesDTO.UserReqDTO;


public interface AccountService {
    void registration(UserReqDTO userReqDTO);
    void updateAccount(UserReqDTO userReqDTO, String email);
    void lockAccount(String email);
}
