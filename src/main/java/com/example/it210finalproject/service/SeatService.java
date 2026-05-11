package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.repository.SeatRepository;
import com.example.it210finalproject.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public int countSeatByStatus(Long tripId, SeatStatus seatStatus) {
        return seatRepository.countByTripIdAndStatus(tripId, seatStatus);
    }

    public List<Seat> findByTripId(Long tripId) {
        return seatRepository.findByTripId(tripId);
    }

    public List<Seat> findAllById(List<Long> ids) {
        return seatRepository.findAllById(ids);
    }

    public void updateStatus(Seat seat) {
        seatRepository.save(seat);
    }

    public boolean existsTicketByTripId(Long tripId) {
        return ticketRepository.existsTicketByTripId(tripId);
    }
}
