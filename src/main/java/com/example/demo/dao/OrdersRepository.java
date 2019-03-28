package com.example.demo.dao;

import com.example.demo.domain.Orders;
import com.example.demo.domain.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends MongoRepository<Orders,String> {
	List<Orders> findByUserId(String id);
	List<Orders> findByBookNameLike(String name);
	List<Orders> findByTimeBetween(Date start, Date end);
}
