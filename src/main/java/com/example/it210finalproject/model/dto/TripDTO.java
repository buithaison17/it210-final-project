package com.example.it210finalproject.model.dto;

import com.example.it210finalproject.validation.StartTimeDuplicate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@StartTimeDuplicate(message = "Thời gian khởi hành bị trùng")
public class TripDTO {
    private Long id;
    @NotNull(message = "Tuyến đường không được trống")
    private Long routeId;
    @NotNull(message = "Xe bus không được trống")
    private Long busId;
    @NotNull(message = "Giá vé không được trống")
    @Positive(message = "Giá vé không hợp lệ")
    private Double price;
    @NotNull(message = "Thời gian khởi hành không được trống")
    @Future(message = "Thời gian bắt đầu không hợp lệ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
}
