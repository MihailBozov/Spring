package softuni.exam.models.entity;

import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "constellations")
public class Constellation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @OneToMany(mappedBy = "constellation", targetEntity = Star.class)
    List<Star> stars;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<Star> getStars() {
        return stars;
    }
    
    public void setStars(List<Star> stars) {
        this.stars = stars;
    }
}
