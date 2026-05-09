package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.model.entity.Route;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Top5Trip {
    Route route;
    LocalDateTime startTime;
    Long totalTicketPaid;
    Double revenue;
}
