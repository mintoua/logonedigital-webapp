package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "access_token")
public class AccessToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;
    private Boolean isEnabled;
    private Boolean isExpired;
    @Column(columnDefinition = "datetime")
    private Instant createdAt;
    @Column(columnDefinition = "datetime")
    private Instant ExpiredAt;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
