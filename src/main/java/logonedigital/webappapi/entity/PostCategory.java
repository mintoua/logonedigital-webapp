package logonedigital.webappapi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
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
@Table(name = "categories_post")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idPostCategory")
public class PostCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idPostCategory;
    private String title;
    private String slug;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postCategory")
    private List<Post> posts;
}
