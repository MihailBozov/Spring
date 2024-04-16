package softuni.exam.models.entity;

import softuni.exam.models.enums.StarType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stars")
public class Star {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "light_years", nullable = false)
    private double lightYears;
    
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "star_type", nullable = false)
    @Enumerated(EnumType.STRING)
    StarType starType;
    
    @ManyToOne
    @JoinColumn(name = "constellation_id", referencedColumnName = "id")
    private Constellation constellation;
    
    @OneToMany(mappedBy = "observingStar", targetEntity = Astronomer.class)
    private List<Astronomer> observers;
    
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
    
    public double getLightYears() {
        return lightYears;
    }
    
    public void setLightYears(double lightYears) {
        this.lightYears = lightYears;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public StarType getStarType() {
        return starType;
    }
    
    public void setStarType(StarType starType) {
        this.starType = starType;
    }
    
    public Constellation getConstellation() {
        return constellation;
    }
    
    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
    
    public List<Astronomer> getObservers() {
        return observers;
    }
    
    public void setObservers(List<Astronomer> observers) {
        this.observers = observers;
    }
}
