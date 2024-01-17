package logonedigital.webappapi.dto.accountFeaturesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
