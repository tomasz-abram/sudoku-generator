package com.tabram.sudokusolver.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileValidation.class})
public @interface FileValid {
    String message() default "Only json file are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}