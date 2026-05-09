package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.TicketService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/cancel-ticket")
    public String cancelTicket(
            @RequestParam("id") Long ticketId,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        Ticket ticket = ticketService.findById(ticketId);
        ticketService.cancelTicket(ticket);
        redirectAttributes.addFlashAttribute("success", "Huỷ vé thành công");
        return switch (user.getRole()) {
            case PASSENGER -> "redirect:/tickets";
            case ADMIN -> "redirect:/admin/tickets";
            case STAFF -> "redirect:/staff/tickets";
        };
    }

    @GetMapping("/confirm-ticket")
    public String confirmTicket(
            @RequestParam("id") Long id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        Ticket ticket = ticketService.findById(id);
        ticketService.confirmTicket(ticket);
        redirectAttributes.addFlashAttribute("success", "Xác nhận vé thành công");
        return switch (user.getRole()) {
            case PASSENGER -> "redirect:/tickets";
            case ADMIN -> "redirect:/admin/tickets";
            case STAFF -> "redirect:/staff/tickets";
        };
    }
}
