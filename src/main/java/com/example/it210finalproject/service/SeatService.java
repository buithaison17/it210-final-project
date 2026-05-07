package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public int countSeatByStatus(Long busId, SeatStatus seatStatus) {
        return seatRepository.countByBusIdAndStatus(busId, seatStatus);
    }

    public List<Seat> findByBusId(Long busId) {
        return seatRepository.findByBusId(busId);
    }

    public List<Seat> findAllById(List<Long> ids) {
        return seatRepository.findAllById(ids);
    }

    public void updateStatusAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }
}
