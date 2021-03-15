package com.cyan.modclima.annotations;

import com.cyan.modclima.annotations.validations.StartDateBeforeEndDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateBeforeEndDateValidator.class)
public @interface StartDateBeforeEndDate {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Start date cannot be before the end date.";

}
