package com.example.restapihomework.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SensorDTO {
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;
}
