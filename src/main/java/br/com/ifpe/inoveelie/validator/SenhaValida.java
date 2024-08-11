package br.com.ifpe.inoveelie.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhaValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)

public @interface SenhaValida {
    String message() default "A senha deve conter pelo menos 6 caracteres, incluindo letras e números, e não pode conter números em sequência.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
