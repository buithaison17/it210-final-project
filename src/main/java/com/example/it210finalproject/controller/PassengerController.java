package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PassengerController {
    @GetMapping({"", "/", "/trips"})
    public String trips() {
        return "passenger/passenger-trips";
    }

    @GetMapping("/tickets")
    public String tickets() {
        return "passenger/passenger-tickets";
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
}
