package logonedigital.webappapi.dto.blogFeaturesDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDTO {
    @Schema(name = "Post Tile", example = "My title")
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    private String title;
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    @Schema(name = "Post content", example ="description")
    private String content;
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    @Schema(name = "Post Category slug", example ="postCategory-1")
    private String slugPostCategory;
    @Schema(name = "Post Image", example ="my-image.jpg")
    @NotNull(message = "You must upload an image")
    private MultipartFile imageFile;
}
