package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Boolean isActivated;
    @Column(columnDefinition = "datetime")
    private Instant createdAt;
    @Column(columnDefinition = "datetime")
    private Instant updatedAt;

    @ManyToMany
    private List<Role> roles;

}
