package softuni.exam.util.StarTypeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidatorStarType.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorStarTypeAnnotation {
    String message() default "Your starType is wrong";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
