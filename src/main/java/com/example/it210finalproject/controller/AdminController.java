package com.example.it210finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping({"", "/dashboard"})
    public String dashboard() {
        return "admin/admin-dashboard";
    }

    @GetMapping("/users")
    public String users() {
        return "admin/admin-users";
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
