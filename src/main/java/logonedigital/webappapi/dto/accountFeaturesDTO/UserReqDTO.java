package logonedigital.webappapi.dto.accountFeaturesDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDTO {

    private String firstname;
    @NotEmpty(message = "Lastname required!")
    @NotBlank(message = "Lastname couldn't be blank")
    private String lastname;
    @NotEmpty(message = "Email address required!")
    @NotBlank(message = "Email address couldn't be blank")
    @Email(message = "Email address not correct!")
    private String email;
    @NotEmpty(message = "Password required!")
    @NotBlank(message = "Password couldn't be blank")
    @Length(min = 8, message = "Password couldn't least than 8 characters")
    private String password;
    @NotEmpty(message = "Field required!")
    @NotBlank(message = "Field couldn't be blank")
    private String passwordConfirm;
}
