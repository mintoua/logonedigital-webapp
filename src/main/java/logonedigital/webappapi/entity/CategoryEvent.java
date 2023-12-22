package logonedigital.webappapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CategoryEvent  {
    @Id
    private Integer id;
    private String designation;
}
