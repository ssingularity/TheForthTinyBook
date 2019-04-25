package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Bean
    public Exchange orderExchange(){
        return new DirectExchange("orderExchange");
    }

    @Bean
    public Queue orderQueue(){
        //可以通过arguments来设置死信队列
        Map<String, Object> args = new HashMap<>();
        args.put("x-expires", 1800000);
        args.put("x-dead-letter-exchange", "dlx_exchange");
        Queue queue = new Queue("orderQueue", true, false, false, args);
        return queue;
    }

    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order").noargs();
    }

    @Bean
    public Exchange dlxExchange(){
        return new DirectExchange("dlx_exchange");
    }

    @Bean Queue dlxQueue(){
        return new Queue("dlx_queue");
    }

    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("order").noargs();
    }

}
