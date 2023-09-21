package com.projectwydnex.project.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailUnicoValidator.class)
public @interface ValidarEmailUnico {
    String message() default "El correo ya esta registrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
