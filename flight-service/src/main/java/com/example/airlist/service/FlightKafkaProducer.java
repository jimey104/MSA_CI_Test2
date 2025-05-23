package com.example.airlist.service;

import com.example.airlist.dto.FlightDto;
import com.example.airlist.dto.FlightReservationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topicName = "flight-topic";

    public void sendFlightData(FlightReservationDto flightReservationDto) {
        try {
            String json = objectMapper.writeValueAsString(flightReservationDto); // ✅ JSON 문자열 변환
            kafkaTemplate.send(topicName, json); // ✅ 문자열로 전송
            System.out.println("항공편 전송완료: " + flightReservationDto.getReservationId());
        } catch (JsonProcessingException e) {
            System.out.println("❌ Kafka 전송 실패");
            e.printStackTrace();
        }
    }
}

