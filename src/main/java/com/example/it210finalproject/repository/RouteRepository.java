package com.example.it210finalproject.repository;

import com.example.it210finalproject.model.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
