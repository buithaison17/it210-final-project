package com.example.it210finalproject.controller;

import com.example.it210finalproject.enums.Role;
import com.example.it210finalproject.model.dto.LoginForm;
import com.example.it210finalproject.model.dto.RegisterForm;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("loginForm") LoginForm loginForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // Xử lý đăng nhập
        User user = userService.login(loginForm);
        if (user == null) {
            // Thông báo lỗi
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "/login";
        }

        // Lưu vào session
        session.setAttribute("user", user);

        // Điều hướng trang theo Role
        Role role = user.getRole();
        return switch (role) {
            case ADMIN -> "redirect:/admin";
            case STAFF -> "redirect:/staff";
            case PASSENGER -> "redirect:/";
        };
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(registerForm);
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
