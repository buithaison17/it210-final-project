package com.example.it210finalproject.validation;

import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PhoneDuplicateValidation implements ConstraintValidator<PhoneDuplicate, String> {
    private UserService userService;
    HttpSession httpSession;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        User user = userService.findByPhone(value);
        if (user == null) return true;
        User currentUser = (User) httpSession.getAttribute("user");
        if (currentUser == null) return false;
        if (currentUser.getId().equals(user.getId())) return true;
        context.buildConstraintViolationWithTemplate("Số điện thoại đã tồn tại")
                .addPropertyNode("phone")
                .addConstraintViolation();
        return false;
    }
}
