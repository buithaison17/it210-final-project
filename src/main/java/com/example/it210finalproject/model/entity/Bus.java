package com.example.it210finalproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "buses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id", nullable = false)
    private Long id;
    @Column(name = "driver_name", nullable = false)
    private String driverName;
    @Column(name = "company", nullable = false)
    private String company;
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;
    @Column(name = "plate_number", nullable = false)
    private String plateNumber;
    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY)
    private List<Seat> seats;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
}
