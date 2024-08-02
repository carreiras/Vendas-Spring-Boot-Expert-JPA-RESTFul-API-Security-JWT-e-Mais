package carreiras.com.github.java_spring_boot_vendas.validation.constraintvalidation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import carreiras.com.github.java_spring_boot_vendas.validation.NotEmptyList;

import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {

    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }
}
