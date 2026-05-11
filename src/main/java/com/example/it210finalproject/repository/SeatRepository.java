package com.example.it210finalproject.repository;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    int countByTripIdAndStatus(Long tripId, SeatStatus status);

    List<Seat> findByTripId(Long tripId);

    void deleteByBusId(Long busId);

    boolean existsByTripIdAndStatus(Long tripId, SeatStatus status);

    void deleteByTripId(Long tripId);
}
