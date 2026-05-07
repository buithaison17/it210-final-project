package com.example.it210finalproject.service;

import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.repository.SeatRepository;
import com.example.it210finalproject.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void saveAll(List<Ticket> tickets, List<Seat> seats) {
        ticketRepository.saveAll(tickets);
        seatRepository.saveAll(seats);
    }
}
