package com.example.it210finalproject.controller;

import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final TicketService ticketService;

    public StaffController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping({"", "/tickets"})
    public String tickets(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        if (currentPage <= 0) return "redirect:/staff/tickets?currentPage=1";
        Page<Ticket> tickets = ticketService.findAll(currentPage, 5);
        if (tickets.getTotalPages() > 0 && currentPage > tickets.getTotalPages())
            return "redirect:/staff/tickets?currentPage=" + tickets.getTotalPages();
        model.addAttribute("tickets", tickets.getContent());
        model.addAttribute("totalPages", tickets.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return "staff/staff-tickets";
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
