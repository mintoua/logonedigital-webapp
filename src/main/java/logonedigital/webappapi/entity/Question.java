package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title couldn't be empty")
    @NotBlank(message = "Title couldn't be blank")
    @Column(columnDefinition = "TEXT")
    private String question;

    @NotEmpty (message = "Title couldn't be empty")
    @NotBlank(message = "Title couldn't be blank")
    private String subject;

    @NotEmpty (message = "Title couldn't be empty")
    @NotBlank(message = "Title couldn't be blank")
    @Enumerated(EnumType.STRING)
    private TypeQuestion questionType;

    @ElementCollection
    private List<String> choices;

    @ElementCollection
    private List<String> correctAnswers;

}
