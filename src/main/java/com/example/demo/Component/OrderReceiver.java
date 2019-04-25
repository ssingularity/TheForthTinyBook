package com.example.demo.Component;

import com.example.demo.dao.OrdersRepository;
import com.example.demo.domain.Orders;
import com.example.demo.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RabbitListener(queues = "orderQueue")
public class OrderReceiver {
    @Autowired
    OrderService orderService;
    @Autowired
    OrdersRepository ordersRepository;
    @RabbitHandler
    public void handler(Orders orders, Channel channel, Message message){
        System.out.println("RMI: " + orderService.getResult());
        ordersRepository.save(orders);
        try {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
