package com.example.demo.service;

import com.example.demo.dao.BookRepository;
import com.example.demo.dao.OrdersRepository;
import com.example.demo.dao.SysRoleRepository;
import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.Book;
import com.example.demo.domain.Orders;
import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	OrdersRepository ordersRepository;
	@Autowired
	SysUserRepository sysUserRepository;
	@Autowired
	SysRoleRepository sysRoleRepository;

	public void addUser(String username,String password,String description,String phone, String email){
		SysRole role=sysRoleRepository.findByIdEquals(1L);
		List<SysRole> roles=new ArrayList<>();
		roles.add(role);
		SysUser user=new SysUser(username,password,description,phone,email,null,roles);
		sysUserRepository.save(user);
	}

	public List<Orders> showOrder(SysUser user){
		List<Orders> orders=ordersRepository.findByUser_id(user.getId());
		for (Orders o:orders){
			o.setUser(null);
		}
		return orders;
	}

	public boolean isExisted(String name){
		SysUser user=sysUserRepository.findByUsername(name);
		if (user==null) return false;
		else return true;
	}

	public boolean makeOrder(SysUser user,Long id,Integer count){
		Set<Orders> orders=user.getOrders();
		if (orders==null) orders=new HashSet<>();
		Book book=bookRepository.findByIdEquals(id);
		if(book==null) return false;
		Integer storage=book.getStorage();
		if (storage<count) return false;
		else {
			book.setStorage(storage-count);
			bookRepository.save(book);
		}
		Orders order=new Orders(book.getName(),book.getId(),count,count*book.getPrice(),new Date());
		order.setUser(user);
		ordersRepository.save(order);
		orders.add(order);
		user.setOrders(orders);
		sysUserRepository.save(user);
		return true;
	}
}
