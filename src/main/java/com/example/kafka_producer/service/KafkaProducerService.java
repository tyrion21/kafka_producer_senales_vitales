package com.example.kafka_producer.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    
    private static final String TOPIC = "senales-vitales";
    private final Random random = new Random();

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }

    @Scheduled(fixedRate = 10000)  
    public void generateVitalSigns() {
        int heartRate = 60 + random.nextInt(40); 
        int temperature = 36 + random.nextInt(2); 
        int bloodPressure = 100 + random.nextInt(40); 
        
        String vitalSigns = String.format(  
            "{\"frecuenciaCardiaca\":%d,\"temperatura\":%d,\"presionSanguinea\":%d,\"tiempo\":\"%s\"}",
            heartRate, temperature, bloodPressure, System.currentTimeMillis()
        );
        
        sendMessage(vitalSigns);
    }
}
