package com.example.restapihomework.dto;

import com.example.restapihomework.models.Sensor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MeasurementDTO {

    @Min(value = -100, message = "A value should not be less than -100")
    @Max(value = 100, message = "A value should not be more than 100")
    private double value;

    @NotNull(message = "A raining should not be empty")
    private Boolean raining;

    @NotNull(message = "A sensor should not be empty")
    private Sensor sensor;
}
