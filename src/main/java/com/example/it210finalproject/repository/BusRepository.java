package com.example.it210finalproject.repository;

import com.example.it210finalproject.model.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByPlateNumber(String plateNumber);
}
