package com.example.demo.dao;

import com.example.demo.domain.Orders;
import com.example.demo.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long>{
	List<Orders> findByUser_id(Long id);
	List<Orders> findByBookNameLike(String name);
	List<Orders> findByTimeBetween(Date start, Date end);
}
