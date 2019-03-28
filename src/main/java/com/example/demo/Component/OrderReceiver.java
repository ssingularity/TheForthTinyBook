package com.example.demo.Component;

import com.example.demo.dao.BookRepository;
import com.example.demo.dao.OrdersRepository;
import com.example.demo.domain.Orders;
import com.example.demo.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RabbitListener(queues = "order")
public class OrderReceiver {
    @Autowired
    OrderService orderService;
    @Autowired
    OrdersRepository ordersRepository;
    @RabbitHandler
    public void handler(Orders orders){
        System.out.println(orderService.getResult());
        ordersRepository.save(orders);
    }
}
