package com.example.it210finalproject.service;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.dto.BusDTO;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.repository.BusRepository;
import com.example.it210finalproject.repository.SeatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final SeatRepository seatRepository;

    public BusService(BusRepository busRepository, SeatRepository seatRepository) {
        this.busRepository = busRepository;
        this.seatRepository = seatRepository;
    }

    private Bus mapToBus(BusDTO busDTO) {
        return Bus.builder()
                .id(busDTO.getId())
                .driverName(busDTO.getDriverName())
                .company(busDTO.getCompany())
                .totalSeats(busDTO.getTotalSeats())
                .plateNumber(busDTO.getPlateNumber())
                .build();
    }

    public Page<Bus> findAll(Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage);
        return busRepository.findAll(pageable);
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus findById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addBus(BusDTO busDTO) {
        // Thêm xe bus
        Bus bus = mapToBus(busDTO);
        busRepository.save(bus);
    }

    public void updateBus(Long id, BusDTO busDTO) {
        busDTO.setId(id);
        Bus bus = mapToBus(busDTO);
        busRepository.save(bus);
    }

    public Bus findByPlateNumber(String plateNumber) {
        return busRepository.findByPlateNumber(plateNumber);
    }

    @Transactional
    public void deleteBus(Long busId) {
        seatRepository.deleteByBusId(busId);
        busRepository.deleteById(busId);
    }
}
