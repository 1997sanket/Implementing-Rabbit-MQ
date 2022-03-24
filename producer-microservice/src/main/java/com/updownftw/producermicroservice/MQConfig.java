package com.updownftw.producermicroservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String MESSAGE_ROUTING_KEY = "message_routingKey";
    public static final String MESSAGE_EXCHANGE = "message_exchange";
    public static final String MESSAGE_QUEUE = "message_queue";

    // Creating Queue
    @Bean
    public Queue getQueue() {
        return new Queue(MESSAGE_QUEUE);
    }

    // Creating Exchange
    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(MESSAGE_EXCHANGE);
    }

    // Now bind them together
    @Bean
    public Binding getBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(MESSAGE_ROUTING_KEY);
    }

    // Creating a message converter so that our Message is sent as JSON object
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Creating a RabbitMQ template to work with
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
