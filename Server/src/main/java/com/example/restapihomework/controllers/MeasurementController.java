package com.example.restapihomework.controllers;

import com.example.restapihomework.dto.MeasurementDTO;
import com.example.restapihomework.exceptions.ExceptionResponse;
import com.example.restapihomework.exceptions.MeasurementException;
import com.example.restapihomework.models.Measurement;
import com.example.restapihomework.services.MeasurementService;
import com.example.restapihomework.validators.MeasurementDTOValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementDTOValidator measurementDTOValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementDTOValidator measurementDTOValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementDTOValidator = measurementDTOValidator;
    }

    @GetMapping
    public List<Measurement> getMeasurements() {
        return measurementService.getMeasurements();
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        measurementDTOValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                sb.append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementException(sb.toString());
        }
        measurementService.saveMeasurement(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(MeasurementException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setError(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
