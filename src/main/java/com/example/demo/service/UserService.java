package com.example.demo.service;

import com.example.demo.dao.BookRepository;
import com.example.demo.dao.OrdersRepository;
import com.example.demo.dao.SysRoleRepository;
import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.Common.Util.decodeBill;
import static com.example.demo.Common.Util.encodeBill;

@Service
@CacheConfig(cacheNames = "books")
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
	@Autowired
	AmqpTemplate amqpTemplate;

	private boolean isNumber(String s){
		for(int i=0;i<s.length();++i){
			if(!Character.isDigit(s.charAt(i))) return false;
		}
		return true;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
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

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public List<User> getUsers(){
		List<SysUser> sysUsers = sysUserRepository.findAll();
		List<User> users = new ArrayList<>();
		for (SysUser user : sysUsers){
			users.add(new User(user.getId(),user.getUsername(),user.getPassword(),user.getDescription(),user.getPhone(),user.getEmail()));
		}
		return users;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void removeById(String id){
		sysUserRepository.deleteById(id);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public User getByid(String id){
		SysUser user = sysUserRepository.findByIdEquals(id);
		return new User(user.getId(),user.getUsername(),user.getPassword(),user.getDescription(),user.getPhone(),user.getEmail());
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addUser(String username,String password,String description,String phone, String email){
		SysUser user=new SysUser(username,password,description,phone,email,null,null);
		List<SysRole> sysRoles = new ArrayList<>();
		sysRoles.add(new SysRole("admin"));
		user.setRoles(sysRoles);
		sysUserRepository.save(user);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public List<Orders> showOrder(SysUser user){
		List<Orders> orders=ordersRepository.findByUserId(user.getId());
		for (Orders o:orders){
			o.setTotalPrice(decodeBill(o.getTotalPrice()));
		}
		return orders;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public boolean isExisted(String name){
		SysUser user=sysUserRepository.findByUsername(name);
		if (user==null) return false;
		else return true;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@CacheEvict(key = "#id")
	public boolean makeOrder(SysUser user,String id,Integer count){
		//Set<Orders> orders=user.getOrders();
		//if (orders==null) orders=new HashSet<>();
		Book book=bookRepository.findByIdEquals(id);
		if(book==null) return false;
		Integer storage=book.getStorage();
		if (storage<count) return false;
		else {
			book.setStorage(storage-count);
			bookRepository.save(book);
			int totalPrice = encodeBill(count * book.getPrice());
			Orders order=new Orders(book.getName(),book.getId(),count,totalPrice,new Date());
			order.setUserId(user.getId());
			amqpTemplate.convertAndSend("order", order);
			return true;
		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public SysUser updateUser(SysUser user,String password,String description,String phone,String email){
		user.setPassword(password);
		user.setDescription(description);
		user.setPhone(phone);
		user.setEmail(email);
		sysUserRepository.save(user);
		return user;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void updateUserById(String id, String password, String description, String phone, String email){
		SysUser user = sysUserRepository.findByIdEquals(id);
		user.setDescription(description);
		user.setPhone(phone);
		user.setEmail(email);
		sysUserRepository.save(user);
	}

}
