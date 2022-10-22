package com.example.restapihomework.services;

import com.example.restapihomework.models.Measurement;
import com.example.restapihomework.models.Sensor;
import com.example.restapihomework.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> getMeasurements() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void saveMeasurement(Measurement measurement) {
        measurement.setMeasuredAt(LocalTime.now());
        Sensor sensor = sensorService.findByName(measurement.getSensor().getName());
        measurement.setSensor(sensor);
        if (sensor.getMeasurements() == null) {
            sensor.setMeasurements(new ArrayList<>(Collections.singletonList(measurement)));
        } else {
            sensor.getMeasurements().add(measurement);
        }
        measurementRepository.save(measurement);
    }

    public int getRainyDaysCount() {
        return measurementRepository.findAllByIsRainingTrue().size();
    }
}
