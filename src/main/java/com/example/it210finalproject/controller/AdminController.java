package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping({"", "/dashboard"})
    public String dashboard() {
        return "admin/admin-dashboard";
    }

    @GetMapping("/my-profile")
    public String myProfile(
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("editProfileForm", new EditProfileForm(user.getFullName(), user.getEmail(), user.getPhone()));
        return "my-profile";
    }

    @GetMapping("/trips")
    public String trips() {
        return "admin/admin-trips";
    }

    @GetMapping("/tickets")
    public String tickets() {
        return "admin/admin-tickets";
    }

    @GetMapping("/buses")
    public String buses() {
        return "admin/admin-buses";
    }

    @GetMapping("/routes")
    public String routes() {
        return "admin/admin-routes";
    }
}
