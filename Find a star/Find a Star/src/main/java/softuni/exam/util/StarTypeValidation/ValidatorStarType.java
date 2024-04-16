package softuni.exam.util.StarTypeValidation;

import softuni.exam.models.enums.StarType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;

public class ValidatorStarType implements ConstraintValidator<ValidatorStarTypeAnnotation, StarType> {
    
    @Override
    public void initialize(ValidatorStarTypeAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(StarType starType, ConstraintValidatorContext constraintValidatorContext) {
        
        if (starType == null) {
            return false;
        }
        
        return EnumSet.allOf(StarType.class).contains(starType);

//        for (StarType type : StarType.values()) {
//            if (type.equals(starType)) {
//                return true;
//            }
//        }
//        return false;
    }
    
}
