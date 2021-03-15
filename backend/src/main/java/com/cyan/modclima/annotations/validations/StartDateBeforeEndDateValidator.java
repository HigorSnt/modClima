package com.cyan.modclima.annotations.validations;

import com.cyan.modclima.annotations.StartDateBeforeEndDate;
import com.cyan.modclima.models.Harvest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class StartDateBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, Harvest> {
    @Override
    public void initialize(StartDateBeforeEndDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(Harvest harvest, ConstraintValidatorContext constraintValidatorContext) {
        return harvest.getStart().isBefore(harvest.getEnd());
    }
}
