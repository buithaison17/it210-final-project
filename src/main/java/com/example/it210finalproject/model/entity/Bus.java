package com.example.it210finalproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
