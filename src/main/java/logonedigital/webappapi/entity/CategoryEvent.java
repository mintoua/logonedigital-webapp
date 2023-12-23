package logonedigital.webappapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "This field couldn't be null")
    @NotEmpty(message = "This field couldn't be empty")
    @NotBlank(message = "This field couldn't be blank")
    private String designation;
    private String slug;
}
