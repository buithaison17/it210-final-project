package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.repository.SeatRepository;
import com.example.it210finalproject.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final SeatService seatService;

    @Transactional
    public void saveAll(List<Ticket> tickets, List<Seat> seats) {
        ticketRepository.saveAll(tickets);
        seatRepository.saveAll(seats);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Page<Ticket> findByUser(User user, Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return ticketRepository.findByUser(user, pageable);
    }

    public Page<Ticket> findAll(Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> findByStatus(Integer currentPage, Integer perPage, TicketStatus ticketStatus) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return ticketRepository.findByStatus(TicketStatus.PENDING, pageable);
    }

    @Transactional
    public void cancelTicket(Ticket ticket) {
        // Chuyển trạng thái vé
        ticket.setStatus(TicketStatus.CANCELLED);
        Seat seat = ticket.getSeat();
        // Giải phóng ghế
        seat.setStatus(SeatStatus.AVAILABLE);
        // Lưu dữ liệu
        seatService.updateStatus(seat);
        ticketRepository.save(ticket);
    }
}
