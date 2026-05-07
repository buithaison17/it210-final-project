package com.example.it210finalproject.service;

import com.example.it210finalproject.model.dto.TripDTO;
import com.example.it210finalproject.model.entity.Trip;
import com.example.it210finalproject.repository.BusRepository;
import com.example.it210finalproject.repository.RouteRepository;
import com.example.it210finalproject.repository.TripRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;

    public TripService(TripRepository tripRepository, RouteRepository routeRepository, BusRepository busRepository) {
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.busRepository = busRepository;
    }

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

    public void addTrip(TripDTO tripDTO) {
        Trip trip = mapToTrip(tripDTO);
        tripRepository.save(trip);
    }

    public void updateTrip(Long id, TripDTO tripDTO) {
        tripDTO.setId(id);
        Trip trip = mapToTrip(tripDTO);
        tripRepository.save(trip);
    }

    public Page<Trip> findAll(Integer currentPage, Integer perPage) {
        // Phân trang giảm dần theo thời gian tạo
        Pageable pageable = PageRequest.of(currentPage - 1, perPage, Sort.by("createdAt").descending());
        return tripRepository.findAll(pageable);
    }

    public Trip findById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }
}
