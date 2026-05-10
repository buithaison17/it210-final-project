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
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final SeatService seatService;
    private final EmailService emailService;
    private final HttpSession session;

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Page<Ticket> findByUser(User user, Long id, Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return ticketRepository.findByUserAndId(user, id, pageable);
    }

    public Page<Ticket> findAll(Long keyword, Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        if (keyword == null) {
            return ticketRepository.findAll(pageable);
        }
        return ticketRepository.searchById(keyword, pageable);
    }

    public Page<Ticket> findByStatus(Integer currentPage, Integer perPage, TicketStatus ticketStatus) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return ticketRepository.findByStatus(TicketStatus.PENDING, pageable);
    }

    private Ticket mapToTicket(User user, Trip trip, Seat seat, TicketStatus ticketStatus) {
        return Ticket.builder()
                .seat(seat)
                .trip(trip)
                .user(user)
                .status(ticketStatus)
                .price(trip.getPrice())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public void bookTicket(User user, Trip trip, List<Seat> seats, String methodPayment) {
        // Email template
        String seatNames = seats.stream().map(Seat::getSeatNumber).collect(Collectors.joining(", "));
        String content = """
                Cảm ở bạn đã sử dụng dịch vụ của Bus Ticket Pro
                    Thông tin chuyến đi
                    - Mã vé: %d
                    - Nhà xe: %s
                    - Tuyến đường: %s - %s
                    - Thời gian gian khởi hành: %s
                    - Ghế: %s
                    - Tổng tiền: %.2f VNĐ
                    - Hình thức thanh toán: %s
                
                Lưu ý nếu bạn thanh toán tại quầy vui lòng có mặt tại quầy trươc 30 phút để hoàn tât thủ tục.
                Nếu không vé của bạn sẽ bị huỷ.
                Xin trân trọng cảm ơn.
                Chúc bạn có một chuyến an toàn bên gia đình và người thân.
                """.formatted(
                trip.getId(),
                trip.getBus().getCompany(),
                trip.getRoute().getOrigin().getName(),
                trip.getRoute().getDestination().getName(),
                trip.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")),
                seatNames,
                trip.getPrice(),
                methodPayment.equals("cash") ? "Thanh toán tại quầy" : "Chuyển khoản"
        );

        // Thanh toán tiền mặt
        List<Ticket> tickets = new ArrayList<>();
        if (methodPayment.equals("cash")) {
            seats.forEach(seat -> {
                Ticket ticket = mapToTicket(user, trip, seat, TicketStatus.PENDING);
                tickets.add(ticket);
                seat.setTrip(trip);
                seat.setStatus(SeatStatus.PENDING);
            });
        } else {
            seats.forEach(seat -> {
                Ticket ticket = mapToTicket(user, trip, seat, TicketStatus.PAID);
                tickets.add(ticket);
                seat.setTrip(trip);
                seat.setStatus(SeatStatus.BOOKED);
            });
        }

        ticketRepository.saveAll(tickets);
        seatRepository.saveAll(seats);
        emailService.sendEmail(user.getEmail(), "Đặt vé thành công", content);
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

    @Transactional
    public void autoCancelExpiredTickets() {
        // Lấy danh sách các vé quá hạn
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(30);
        List<Ticket> tickets = ticketRepository.findExpiredTickets(deadline);
        // Duyệt qua danh sách và huỷ vé
        for (Ticket ticket : tickets) {
            cancelTicket(ticket);
        }
    }
}
