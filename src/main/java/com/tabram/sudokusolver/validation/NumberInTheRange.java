package com.tabram.sudokusolver.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberInTheRangeValidation.class)
public @interface NumberInTheRange {
    String message() default "Invalid number in the board matrix.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
