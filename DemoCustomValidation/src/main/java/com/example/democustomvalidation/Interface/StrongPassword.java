package com.example.democustomvalidation.Interface;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class) //vào class để tìm tới validation, build nghiệp vụ
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface StrongPassword {   //build giao diện
    String message() default "Mật khẩu không đủ mạnh";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}