package com.example.it210finalproject.service;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketScheduler {
    private final TicketService ticketService;

    @Scheduled(fixedDelay = 60000)
    private void autoCancelTicket() {
        ticketService.autoCancelExpiredTickets();
    }
}
