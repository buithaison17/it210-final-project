package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.dto.TripDTO;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.model.entity.Trip;
import com.example.it210finalproject.repository.BusRepository;
import com.example.it210finalproject.repository.RouteRepository;
import com.example.it210finalproject.repository.SeatRepository;
import com.example.it210finalproject.repository.TripRepository;
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

@Service
@AllArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final SeatRepository seatRepository;

    public Trip mapToTrip(TripDTO tripDTO) {
        return new Trip(
                tripDTO.getId(),
                routeRepository.findById(tripDTO.getRouteId()).orElse(null),
                busRepository.findById(tripDTO.getBusId()).orElse(null),
                tripDTO.getPrice(),
                tripDTO.getStartTime(),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void addTrip(TripDTO tripDTO) {
        Trip trip = mapToTrip(tripDTO);
        // Tạo ghế ngồi cho chuyến
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < trip.getBus().getTotalSeats(); i++) {
            Seat seat = new Seat();
            String seatNumber = String.valueOf(i + 1);
            seat.setSeatNumber(seatNumber.length() == 1 ? "0" + seatNumber : seatNumber);
            seat.setBus(trip.getBus());
            seat.setTrip(trip);
            seat.setStatus(SeatStatus.AVAILABLE);
            seats.add(seat);
        }
        tripRepository.save(trip);
        seatRepository.saveAll(seats);
    }

    public void updateTrip(Long id, TripDTO tripDTO) {
        tripDTO.setId(id);
        Trip data = mapToTrip(tripDTO);
        tripRepository.save(data);
    }

    public Page<Trip> findAll(Integer currentPage, Integer perPage) {
        // Phân trang giảm dần theo thời gian tạo
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return tripRepository.findAll(pageable);
    }

    public Trip findById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Trip findByBusIdAndStartTime(Long busId, LocalDateTime startTime) {
        return tripRepository.findByBusIdAndStartTime(busId, startTime);
    }

    public boolean existsByBusId(Long busId) {
        return tripRepository.existsByBusId(busId);
    }

    public void deleteById(Long id) {
        tripRepository.deleteById(id);
    }
}
