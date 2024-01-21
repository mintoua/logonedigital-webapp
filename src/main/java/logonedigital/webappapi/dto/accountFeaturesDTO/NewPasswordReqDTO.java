package logonedigital.webappapi.dto.accountFeaturesDTO;

public record NewPasswordReqDTO(String email, String code, String newPassword, String newPaswordConfirm ) {
}
