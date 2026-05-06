package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Route;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TripDTO {
    private Long id;
    private Route route;
    private Bus bus;
    private LocalDateTime startTime;
}
