package com.updownftw.producermicroservice;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/publish")
    public String publish(@RequestBody CustomMessage customMessage) {

        customMessage.setId(UUID.randomUUID().toString());
        customMessage.setDate(new Date());

        rabbitTemplate.convertAndSend(MQConfig.MESSAGE_EXCHANGE, MQConfig.MESSAGE_ROUTING_KEY, customMessage);

        return "Message sent successfully :)";

    }
}
