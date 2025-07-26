package com.ecomerce.notification.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeOrderEvent(Map<String, Object> orderEvent) {
        System.out.println("Consuming Order Event");
        System.out.println(orderEvent);
    }
}
