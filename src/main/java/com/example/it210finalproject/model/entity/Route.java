package com.example.it210finalproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "location_id", nullable = false)
    private Location origin;
    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "location_id", nullable = false)
    private Location destination;
    @Column(name = "distance", nullable = false)
    private Double distance;
}
