package com.example.it210finalproject.controller;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.*;
import com.example.it210finalproject.service.SeatService;
import com.example.it210finalproject.service.TicketService;
import com.example.it210finalproject.service.TripService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class PassengerController {
    private final TripService tripService;
    private final SeatService seatService;
    private final TicketService ticketService;

    @GetMapping({"", "/", "/trips"})
    public String trips(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        if (currentPage <= 0) return "redirect:/trips?currentPage=1";
        Page<Trip> trips = tripService.findAll(currentPage, 5);
        if (trips.getTotalPages() > 0 && currentPage > trips.getTotalPages())
            return "redirect:/trips?currentPage=" + trips.getTotalPages();
        model.addAttribute("trips", trips.getContent());
        model.addAttribute("totalPages", trips.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        // Lấy số lượng ghế còn trống của từng xe
        List<Integer> seats = new ArrayList<>();
        for (Trip trip : trips.getContent()) {
            seats.add(seatService.countSeatByStatus(trip.getBus().getId(), SeatStatus.AVAILABLE));
        }
        model.addAttribute("seats", seats);
        return "passenger/passenger-trips";
    }

    @GetMapping("/tickets")
    public String tickets(
            Model model,
            HttpSession session,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        if (currentPage <= 0) return "redirect:/tickets?currentPage=1";
        User user = (User) session.getAttribute("user");
        Page<Ticket> tickets = ticketService.findByUser(user, currentPage, 5);
        if (tickets.getTotalPages() > 0 && currentPage > tickets.getTotalPages())
            return "redirect:/tickets?currentPage=" + tickets.getTotalPages();
        model.addAttribute("tickets", tickets.getContent());
        model.addAttribute("totalPages", tickets.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return "passenger/passenger-tickets";
    }

    @GetMapping("/choose-seat/{id}")
    public String seats(
            @PathVariable Long id,
            Model model
    ) {
        Trip trip = tripService.findById(id);
        List<Seat> seats = seatService.findByBusId(trip.getBus().getId());
        model.addAttribute("trip", trip);
        model.addAttribute("seats", seats);
        return "passenger/passenger-seats";
    }

    @PostMapping("/choose-seat")
    public String bookTickets(
            @RequestParam("tripId") Long tripId,
            @RequestParam("seatIds") String ids,
            HttpSession session
    ) {
        List<Long> seatIds = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        List<Seat> seats = seatService.findAllById(seatIds);
        Trip trip = tripService.findById(tripId);
        session.setAttribute("seats", seats);
        session.setAttribute("trip", trip);
        return "redirect:/book-ticket";
    }

    @GetMapping("/book-ticket")
    public String bookTicket(Model model, HttpSession session) {
        List<Seat> seats = (List<Seat>) session.getAttribute("seats");
        Trip trip = (Trip) session.getAttribute("trip");
        model.addAttribute("seats", seats);
        model.addAttribute("trip", trip);
        return "passenger/passenger-book-ticket";
    }

    @PostMapping("/book-ticket")
    public String bookTicket(
            HttpSession session,
            @RequestParam(name = "payment") String payment
    ) {
        User user = (User) session.getAttribute("user");
        Trip trip = (Trip) session.getAttribute("trip");
        List<Seat> seats = (List<Seat>) session.getAttribute("seats");
        List<Ticket> tickets = new ArrayList<>();
        // Thanh toán tại quầy
        if (payment.equals("cash")) {
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
            // Thanh toán online
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
        ticketService.saveAll(tickets, seats);
        return "redirect:/tickets";
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

    @GetMapping("/ticket/{id}")
    public String search(
            @PathVariable Long id,
            Model model
    ) {
        Ticket ticket = ticketService.findById(id);
        model.addAttribute("ticket", ticket);
        return "passenger/passenger-ticket-detail";
    }
}
