package com.example.it210finalproject.repository;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    int countByBusIdAndStatus(Long busId, SeatStatus status);

    List<Seat> findByBusId(Long busId);
}
