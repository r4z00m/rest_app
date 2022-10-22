package com.example.restapihomework.controllers;

import com.example.restapihomework.dto.SensorDTO;
import com.example.restapihomework.exceptions.ExceptionResponse;
import com.example.restapihomework.exceptions.SensorExceptions;
import com.example.restapihomework.models.Sensor;
import com.example.restapihomework.services.SensorService;
import com.example.restapihomework.validators.SensorDTOValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SensorController {

    private final SensorService sensorService;
    private final SensorDTOValidator sensorDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorDTOValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorDTOValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/sensors/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {
        sensorDTOValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                sb.append(error.getDefaultMessage()).append(";");
            }
            throw new SensorExceptions(sb.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(SensorExceptions e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setError(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
