package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.enums.SeatStatus;
import com.example.it210finalproject.model.entity.Trip;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatDTO {
    private Long id;
    private Trip trip;
    private String seatNumber;
    private SeatStatus status;
}
