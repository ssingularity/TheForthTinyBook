package com.example.demo.service;

import com.example.demo.dao.OrdersRepository;
import com.example.demo.domain.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RestfulOrderService {
    @Autowired
    OrdersRepository ordersRepository;
    public List<Orders> getOrders(String query){
        return query == null ? getOrdersWithoutCondition() : getOrdersWithCondition(query);
    }

    private List<Orders> getOrdersWithoutCondition(){
        return ordersRepository.findAll();
    }

    private List<Orders> getOrdersWithCondition(String query){
        return ordersRepository.findByBookNameLike("*" + query + "*");
    }

    public Orders getOrder(String id){
        return ordersRepository.findOrdersById(id);
    }

    public Orders addOrder(Orders orders){
        orders.setTime(new Date());
        return ordersRepository.save(orders);
    }

    public Orders modifyOrder(String id, Orders orders){
        return ordersRepository.save(orders);
    }

    public void deleteOrder(String id){

    }
}
