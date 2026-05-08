package com.example.it210finalproject.validation;

import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator implements ConstraintValidator<EmailDuplicate, String> {
    private final UserService userService;
    private final HttpSession session;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        User user = userService.findByEmail(email);
        // Email chưa tồn tại
        if (user == null) {
            return true;
        }

        // Lấy user hiện tại trong session
        User currentUser = (User) session.getAttribute("user");

        // Nếu chưa login hoặc register
        if (currentUser == null) {
            return false;
        }

        // Nếu là email của chính user hiện tại
        return user.getId().equals(currentUser.getId());
    }
}