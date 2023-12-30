package logonedigital.webappapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Setter
@Getter
@Entity
@Table(name = "files_path")
public class FileData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private String type;
    private String filePath;
}
