package com.example.demo.web;

import com.example.demo.domain.Orders;
import com.example.demo.service.OrderService;
import com.example.demo.service.RestfulOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    RestfulOrderService restfulOrderService;

    @GetMapping("")
    public List<Orders> getOrders(@RequestParam(name = "q", required = false) String query){
        return restfulOrderService.getOrders(query);
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable(name = "id") String id){
        return restfulOrderService.getOrder(id);
    }

    @PostMapping("")
    public Orders makeOrder(@RequestBody Orders order){
        return restfulOrderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public Orders modifyOrder(@PathVariable(name = "id") String id, @RequestBody Orders order){
        return restfulOrderService.modifyOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable(name = "id") String id){
        restfulOrderService.deleteOrder(id);
    }
}
