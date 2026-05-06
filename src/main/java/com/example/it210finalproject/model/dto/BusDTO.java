package com.example.it210finalproject.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusDTO {
    private Long id;
    @NotBlank(message = "Tên tài xế không được để trống")
    private String driverName;
    @NotBlank(message = "Tên công ty không được để trống")
    private String company;
    @NotNull(message = "Số lượng ghế không được để trống")
    private Integer totalSeats;
}
