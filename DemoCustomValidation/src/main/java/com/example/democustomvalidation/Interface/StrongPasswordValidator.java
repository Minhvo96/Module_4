package com.example.democustomvalidation.Interface;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator <StrongPassword, String> {
    @Override
    public void initialize(StrongPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        // Kiểm tra các yêu cầu mật khẩu mạnh ở đây (ít nhất 8 ký tự, chữ hoa, chữ thường, số, ký tự đặc biệt)
        return value.length() >= 8 && value.matches(".*[A-Z].*") && value.matches(".*[a-z].*") &&
                value.matches(".*\\d.*") && value.matches(".*[@#$%^&+=].*");
    }

}
