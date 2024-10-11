package com.coderblack.hms.common.contraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;


public class EnumValidator implements ConstraintValidator<EnumConstraint, String> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumConstraint constraintAnnotation){
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        if(value == null){
            return true;
        }

        return Arrays.stream(enumClass.getEnumConstants()).anyMatch((e)-> e.name().equalsIgnoreCase(value));
    }
}
