package carreiras.com.github.vendas.validation.constraintvalidation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import carreiras.com.github.vendas.validation.NotEmptyList;

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
