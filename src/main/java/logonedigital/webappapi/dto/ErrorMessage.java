package logonedigital.webappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ErrorMessage {
    private String message;
    private int status;
    private Date timestamp;
    private String error;
}
