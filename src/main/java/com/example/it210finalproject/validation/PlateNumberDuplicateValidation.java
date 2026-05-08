package com.example.it210finalproject.validation;

import com.example.it210finalproject.model.dto.BusDTO;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.service.BusService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlateNumberDuplicateValidation implements ConstraintValidator<PlateNumberDuplicate, BusDTO> {
    private BusService busService;

    @Override
    public boolean isValid(BusDTO value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (value.getPlateNumber() == null) return true;
        Bus bus = busService.findByPlateNumber(value.getPlateNumber());
        if (bus == null) return true;
        context.buildConstraintViolationWithTemplate("Biển số xe đã tồn tại")
                .addPropertyNode("plateNumber")
                .addConstraintViolation();
        return bus.getId().equals(value.getId());
    }
}
