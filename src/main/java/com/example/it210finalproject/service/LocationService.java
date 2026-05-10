package com.example.it210finalproject.service;

import com.example.it210finalproject.model.entity.Location;
import com.example.it210finalproject.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}
