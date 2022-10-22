package com.example.restapihomework.validators;

import com.example.restapihomework.dto.MeasurementDTO;
import com.example.restapihomework.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (sensorService.findByName(measurementDTO.getSensor().getName()) == null) {
            errors.rejectValue("sensor", "", "A sensor with the name not registered!");
        }
    }
}
