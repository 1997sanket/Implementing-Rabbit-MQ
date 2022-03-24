package com.updownftw.consumermicroservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

    @RabbitListener(queues = MQConfig.MESSAGE_QUEUE)
    public void subscriber(CustomMessage customMessage) {
        System.out.println("message from publisher: " + customMessage.getMessage());
    }
}
