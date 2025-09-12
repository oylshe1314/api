package com.sk.op.api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegersValidator implements ConstraintValidator<Integers, Integer> {

    private String message;

    private Set<Integer> values;

    @Override
    public void initialize(Integers integers) {
        this.values = new LinkedHashSet<>();
        for (int value : integers.value()) {
            values.add(value);
        }
        this.message = integers.message();
        if (!StringUtils.hasText(this.message) && this.values.size() > 0) {
            this.message = "允许值：" + this.values.stream().map(String::valueOf).collect(Collectors.joining(", ", "{", "}"));
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || this.values.contains(value)) {
            return true;
        }

        if (context.getDefaultConstraintMessageTemplate().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message).addConstraintViolation();
        }
        return false;
    }
}
