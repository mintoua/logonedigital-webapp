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
@Table(name = "refresh_tokens")
public class RefreshToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;
    @Column(columnDefinition = "datetime")
    private Instant createdAt;
    @Column(columnDefinition = "datetime")
    private Instant expiredAt;
    private Boolean isExpired;
}
