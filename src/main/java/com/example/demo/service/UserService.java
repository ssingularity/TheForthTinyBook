package com.example.demo.service;

import com.example.demo.dao.BookRepository;
import com.example.demo.dao.OrdersRepository;
import com.example.demo.dao.SysRoleRepository;
import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	@Autowired
	OrderService orderService;

	private boolean isNumber(String s){
		for(int i=0;i<s.length();++i){
			if(!Character.isDigit(s.charAt(i))) return false;
		}
		return true;
	}

	public List<Orders> getStatistics(String query){
		int index=query.indexOf('~');
		if(index==-1){
			query='%'+query+'%';
			return ordersRepository.findByBookNameLike(query);
		}
		else {
			String start=query.substring(0,index);
			String end=query.substring(index+1,query.length());
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date dstart=simpleDateFormat.parse(start);
				Date dend=simpleDateFormat.parse(end);
				return ordersRepository.findByTimeBetween(dstart,dend);
			}
			catch (ParseException ex){
				return null;
			}
		}
	}

	public List<User> getUsers(){
		List<SysUser> sysUsers = sysUserRepository.findAll();
		List<User> users = new ArrayList<>();
		for (SysUser user : sysUsers){
			users.add(new User(user.getId(),user.getUsername(),user.getPassword(),user.getDescription(),user.getPhone(),user.getEmail()));
		}
		return users;
	}

	public void removeById(Long id){
		sysUserRepository.deleteById(id);
	}

	public User getByid(Long id){
		SysUser user = sysUserRepository.getOne(id);
		return new User(user.getId(),user.getUsername(),user.getPassword(),user.getDescription(),user.getPhone(),user.getEmail());
	}

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
		//Set<Orders> orders=user.getOrders();
		//if (orders==null) orders=new HashSet<>();
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
		System.out.println(orderService.getResult());

		//orders.add(order);
		//user.setOrders(orders);
		//sysUserRepository.save(user);
		return true;
	}

	public SysUser updateUser(SysUser user,String password,String description,String phone,String email){
		user.setPassword(password);
		user.setDescription(description);
		user.setPhone(phone);
		user.setEmail(email);
		sysUserRepository.save(user);
		user.setOrders(null);
		user.setRoles(null);
		return user;
	}

	public void updateUserById(Long id, String password, String description, String phone, String email){
		SysUser user = sysUserRepository.getOne(id);
		user.setDescription(description);
		user.setPhone(phone);
		user.setEmail(email);
		sysUserRepository.save(user);
	}

}
