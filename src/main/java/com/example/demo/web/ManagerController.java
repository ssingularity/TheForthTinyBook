package com.example.demo.web;

import com.example.demo.dao.ImageRepository;
import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.*;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ManagerController {
	@Autowired
	BookService bookService;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	UserService userService;

	@RequestMapping("/getUserById")
	public User getUserById(@RequestParam(name="id") String id){
		return userService.getByid(id);
	}

	@RequestMapping("updateUserById")
	public void updateUserById(@RequestParam(name="id") String id,
	                           @RequestParam(name="password") String password,
	                          @RequestParam(name="description")String description,
	                          @RequestParam(name="phone")String phone,
	                          @RequestParam(name="email")String email){
		userService.updateUserById(id, password, description, phone, email);
	}

	@RequestMapping("/addbook")
	public void bookadd(@RequestParam(name="name") String name,
	                    @RequestParam(name="writer") String writer,
	                    @RequestParam(name="price")Integer price,
	                    @RequestParam(name="storage")Integer storage,
	                    @RequestParam(name="description",defaultValue = "")String description,
	                    @RequestParam(name="publishTime") String publishTime,
	                    @RequestParam(name="img")MultipartFile file
	                    ){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
			FileUtils.writeByteArrayToFile(new File(classPath+name+".jpg"),file.getBytes());
			Image image = new Image(name, file.getBytes());
			imageRepository.save(image);
			bookService.addBook(name,writer,price,storage,description,dpublishTime);
		}
		catch (Exception ex){
			return;
		}
	}

	@RequestMapping("/statistics")
	public List<Orders> getStatistics(@RequestParam(name="query") String query){
		List<Orders> orders = userService.getStatistics(query);
		Map<String, Orders> map = new HashMap<>();
		for (Orders order : orders){
			if (map.containsKey(order.getBookName())){
				Orders o = map.get(order.getBookName());
				o.setCount(o.getCount() + order.getCount());
				o.setTotalPrice(o.getTotalPrice() + order.getTotalPrice());
			}
			else {
				map.put(order.getBookName(), order);
			}
		}
		List<Orders> result = new LinkedList<>();
		for (Orders order : map.values()) {
			result.add(order);
		}
		return result;
	}

	@RequestMapping("/getUsers")
	public List<User> getUsers(){
		return userService.getUsers();
	}

	@RequestMapping("/removeUser")
	public void deleteUser(@RequestParam(name="id") String id){
		userService.removeById(id);
	}

	@RequestMapping("/removebook")
	public void delete(@RequestParam(name="id") String id){
		bookService.removeBook(id);
	}

	@RequestMapping("/updatebook")
	public Book updatebook(@RequestParam(name="id")String id,
	                       @RequestParam(name = "name") String name,
	                    @RequestParam(name="writer") String writer,
	                    @RequestParam(name="price")Integer price,
	                    @RequestParam(name="storage")Integer storage,
	                    @RequestParam(name="description")String description,
					    @RequestParam(name="publishTime")String publishTime,
	                    @RequestParam(name="img")MultipartFile file
	){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dpublishTime=simpleDateFormat.parse(publishTime);
			String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
			FileUtils.writeByteArrayToFile(new File(classPath+name+".jpg"),file.getBytes());
			return bookService.updateBook(id,name,writer,price,storage,description,dpublishTime);
		}
		catch (Exception ex){
			return null;
		}
	}


}

