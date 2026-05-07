package com.example.it210finalproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id", nullable = false)
    private Route route;
    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id", nullable = false)
    private Bus bus;
    @Column(name = "price", nullable = false, columnDefinition = "decimal(10,2)")
    private Double price;
    @Column(name = "start_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime startTime;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
}
