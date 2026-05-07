package com.example.it210finalproject.service;

import com.example.it210finalproject.model.entity.Route;
import com.example.it210finalproject.repository.RouteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Page<Route> findAll(Integer currentPage, Integer perPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, perPage);
        return routeRepository.findAll(pageable);
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }
}
