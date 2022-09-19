package com.tabram.sudokusolver.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberIsValidPlacementValidation.class)
public @interface NumberIsValidPlacement {

    String message() default "Duplicate number in a row or column or box";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
