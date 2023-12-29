package logonedigital.webappapi.dto.blogFeaturesDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDTO {
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    private String title;
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    private String content;
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    private String slugPostCategory;
    private MultipartFile imageFile;
}
