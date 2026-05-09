package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.dto.Top5User;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.Trip;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void bookTicket(User user, Trip trip, List<Seat> seats, String methodPayment) {
        // Thanh toán tiền mặt
        List<Ticket> tickets = new ArrayList<>();
        if (methodPayment.equals("cash")) {
            seats.forEach(seat -> {
                Ticket ticket = Ticket.builder()
                        .seat(seat)
                        .trip(trip)
                        .user(user)
                        .status(TicketStatus.PENDING)
                        .price(trip.getPrice())
                        .createdAt(LocalDateTime.now())
                        .build();
                tickets.add(ticket);
                seat.setTrip(trip);
                seat.setStatus(SeatStatus.PENDING);
            });
        } else {
            seats.forEach(seat -> {
                Ticket ticket = Ticket.builder()
                        .seat(seat)
                        .trip(trip)
                        .user(user)
                        .status(TicketStatus.PAID)
                        .price(trip.getPrice())
                        .createdAt(LocalDateTime.now())
                        .build();
                tickets.add(ticket);
                seat.setTrip(trip);
                seat.setStatus(SeatStatus.BOOKED);
            });
        }

        ticketRepository.saveAll(tickets);
        seatRepository.saveAll(seats);
    }

    @Transactional
    public void confirmTicket(Ticket ticket) {
        ticket.setStatus(TicketStatus.PAID);
        Seat seat = ticket.getSeat();
        seat.setStatus(SeatStatus.BOOKED);
        ticketRepository.save(ticket);
        seatRepository.save(seat);
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

    public Double getRevenue() {
        return ticketRepository.getRevenue();
    }

    public Integer countByStatus(TicketStatus ticketStatus) {
        return ticketRepository.countByStatus(ticketStatus);
    }

    public List<Top5User> getTop5Users() {
        return ticketRepository.getTop5Users(PageRequest.of(0, 5));
    }
}
