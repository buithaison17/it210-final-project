package com.example.it210finalproject.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive(message = "Số lượng ghế phải là số dương")
    private Integer totalSeats;
    @NotBlank(message = "Biển số xe không được để trống")
    private String plateNumber;
}
