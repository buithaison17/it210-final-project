package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/edit-profile")
    public String editProfile(
            @Valid @ModelAttribute("editProfileForm") EditProfileForm editProfileForm,
            BindingResult bindingResult,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "my-profile";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        userService.updateProfile(user, editProfileForm);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công");
        return switch (user.getRole()) {
            case ADMIN -> "redirect:/admin/my-profile";
            case STAFF -> "redirect:/staff/my-profile";
            case PASSENGER -> "redirect:/my-profile";
        };
    }
}
