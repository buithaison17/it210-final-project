package com.example.it210finalproject.model.dto;


import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.entity.Seat;
import com.example.it210finalproject.model.entity.Trip;
import com.example.it210finalproject.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TicketDTO {
    private Long id;
    private User user;
    private Trip trip;
    private Seat seat;
    private TicketStatus status;
    private Double price;
    private LocalDateTime createdAt;
}
