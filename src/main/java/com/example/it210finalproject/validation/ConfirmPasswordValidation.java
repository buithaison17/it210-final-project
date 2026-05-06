package com.example.it210finalproject.validation;

import com.example.it210finalproject.model.dto.RegisterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidation implements ConstraintValidator<ConfirmPasswordValidate, RegisterForm> {
    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String password = value.getPassword();
        String confirmPassword = value.getConfirmPassword();
        if (password == null || confirmPassword == null) {
            return false;
        }
        context.buildConstraintViolationWithTemplate("Mật khẩu không giống nhau")
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();
        return password.equals(confirmPassword);
    }
}
