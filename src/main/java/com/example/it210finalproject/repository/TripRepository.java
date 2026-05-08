package com.example.it210finalproject.repository;

import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Trip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findByBusIdAndStartTime(Long busId, LocalDateTime startTime);

    boolean existsByBusId(Long busId);
}
