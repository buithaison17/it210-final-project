package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.service.TicketService;
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
            RedirectAttributes redirectAttributes
    ) {
        Ticket ticket = ticketService.findById(ticketId);
        ticketService.cancelTicket(ticket);
        redirectAttributes.addFlashAttribute("success", "Huỷ vé thành công");
        return "redirect:/trips";
    }
}
