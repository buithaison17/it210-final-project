package com.example.it210finalproject.validation;

import com.example.it210finalproject.model.dto.TripDTO;
import com.example.it210finalproject.model.entity.Trip;
import com.example.it210finalproject.service.TripService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartTimeDuplicateValidation implements ConstraintValidator<StartTimeDuplicate, TripDTO> {
    private TripService tripService;

    @Override
    public boolean isValid(TripDTO value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getBusId() == null || value.getStartTime() == null) return true;
        Trip trip = tripService.findByBusIdAndStartTime(value.getBusId(), value.getStartTime());
        context.buildConstraintViolationWithTemplate("Thời gian khởi hành bị trùng")
                .addPropertyNode("startTime")
                .addConstraintViolation();
        if (trip == null) return true;
        return trip.getId().equals(value.getId());
    }
}
