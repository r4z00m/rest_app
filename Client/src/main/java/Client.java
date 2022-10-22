import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {

    public static final String REGISTRATION = "http://localhost:8080/sensors/registration";
    public static final String ADD_MEASUREMENTS = "http://localhost:8080/measurements/add";
    public static final String GET_MEASUREMENTS = "http://localhost:8080/measurements";
    public static final String SENSOR_NAME = "sensor";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        addSensor(restTemplate);
        addMeasurements(restTemplate);

        ResponseEntity<Measurements[]> response =  restTemplate
                .getForEntity(GET_MEASUREMENTS, Measurements[].class);
        Measurements[] responses = response.getBody();

        if (responses != null) {
            showTemperatures(responses);
        }
    }

    private static void showTemperatures(Measurements[] responses) {
        double[] xData = new double[responses.length];
        double[] yData = new double[responses.length];
        for (int i = 0; i < responses.length; i++) {
            Measurements measurement = responses[i];
            xData[i] = i;
            yData[i] = measurement.getValue();
        }

        XYChart chart = QuickChart.getChart(
                "Temperatures",
                "x",
                "y",
                "t",
                xData, yData);
        new SwingWrapper<>(chart).displayChart();
    }

    private static void addMeasurements(RestTemplate restTemplate) {
        ObjectMapper mapper = new ObjectMapper();
        Random random = new Random();
        Sensor sensor = new Sensor();
        sensor.setName(SENSOR_NAME);
        Request postRequest = new Request();
        postRequest.setSensor(sensor);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (int i = 0; i < 1000; i++) {
            postRequest.setRaining(random.nextBoolean());
            if (random.nextBoolean()) {
                postRequest.setValue(random.nextDouble(100));
            } else {
                postRequest.setValue(random.nextDouble(100) * -1);
            }
            try {
                HttpEntity<String> entity = new HttpEntity<>(
                        mapper.writeValueAsString(postRequest), headers);
                restTemplate.postForObject(ADD_MEASUREMENTS, entity, String.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addSensor(RestTemplate restTemplate) {
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", SENSOR_NAME);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        try {
            restTemplate.postForObject(REGISTRATION, request, String.class);
            System.out.println("The sensor added");
        } catch (RestClientException ignored) {
            System.out.println("This sensor exist");
        }
    }
}
