package com.compassuol.sp.challenge.msuser.web.producer;

import com.compassuol.sp.challenge.msuser.web.dto.NotificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserProducerNotification {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    public void send(NotificationDto notificationDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] messageBytes = null;
        try {
            messageBytes = objectMapper.writeValueAsBytes(notificationDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting notificationDto to bytes");
        }
        if (messageBytes != null) {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, messageBytes);
        }
    }
}
