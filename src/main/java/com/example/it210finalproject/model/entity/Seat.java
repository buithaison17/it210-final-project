package com.example.it210finalproject.model.entity;

import com.example.it210finalproject.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id", nullable = false)
    private Bus bus;
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id", nullable = false)
    private Trip trip;
    @Version
    private Long version;
}
