package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activations")
public class Activation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(columnDefinition = "datetime")
    private Instant createdAt;
    @Column(columnDefinition = "datetime")
    private Instant expiredAt;
    @Column(columnDefinition = "datetime")
    private Instant activatedAt;
    private String activationCode;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
