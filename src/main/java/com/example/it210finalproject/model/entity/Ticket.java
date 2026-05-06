package com.example.it210finalproject.model.entity;

import com.example.it210finalproject.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id", nullable = false)
    private Trip trip;
    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id", nullable = false)
    private Seat seat;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    @Column(name = "price", nullable = false, columnDefinition = "decimal(10,2) check (price > 0)")
    private Double price;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
