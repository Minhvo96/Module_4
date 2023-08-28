package com.example.democustomvalidation.Interface;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface StrongPassword {
    String message() default "Mật khẩu không đủ mạnh";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}