package softuni.exam.models.dto.jsonDtos;

import com.google.gson.annotations.Expose;
import softuni.exam.models.enums.StarType;
import softuni.exam.util.StarTypeValidation.ValidatorStarTypeAnnotation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class StarDto implements Serializable {
    
    @Expose
    @NotNull
    @Size(min = 6)
    private String description;
    
    @Expose
    @NotNull
    @Positive
    private double lightYears;
    
    @Expose
    @NotNull
    @Size(min = 2, max = 30)
    private String name;
    
    @Expose
    @NotNull
    @ValidatorStarTypeAnnotation
    StarType starType;
    
    @Expose
    @NotNull
    @Positive
    private long constellation;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getLightYears() {
        return lightYears;
    }
    
    public void setLightYears(double lightYears) {
        this.lightYears = lightYears;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public StarType getStarType() {
        return starType;
    }
    
    public void setStarType(StarType starType) {
        this.starType = starType;
    }
    
    public long getConstellation() {
        return constellation;
    }
    
    public void setConstellation(long constellation) {
        this.constellation = constellation;
    }
}
