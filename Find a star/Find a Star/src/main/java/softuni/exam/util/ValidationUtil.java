package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationUtil {
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    public <E> String validate(E e) {
        Set<ConstraintViolation<E>> validations = validator.validate(e);
        
        if (validations.isEmpty()) {
            return null;
        }
        
        return validations
                .stream()
                .map((validation -> validation.getMessage()))
                .collect(Collectors.joining("\n"));
    }
}
