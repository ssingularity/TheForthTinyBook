package com.example.demo.web;

import com.example.demo.domain.*;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import javax.servlet.http.HttpSession;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;
	@RequestMapping("/isadmin")
	public boolean isadmin(){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		if(user.getUsername().equals("admin")) return true;
		else return false;
	}
	@RequestMapping("/logup")
	public String logup(@RequestParam(name="username")String username,
	                  @RequestParam(name="password")String password,
	                  @RequestParam(name="description")String description,
	                  @RequestParam(name="phone")String phone,
	                  @RequestParam(name="email")String email){
			userService.addUser(username,password,description,phone,email);
			return "注册成功";
	}

	@RequestMapping("/makeorder")
	public boolean makeorder(@RequestParam(name="id")Long id,
							@RequestParam(name="count")Integer count){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		if (userService.makeOrder(user,id,count)) return true;
		else return false;
	}

	@RequestMapping("/showorder")
	public List<Orders> showorder(){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		return userService.showOrder(user);
	}

	@RequestMapping("/updateuser")
	public void updateUser(@RequestParam(name="password") String password,
	                          @RequestParam(name="description")String description,
	                          @RequestParam(name="phone")String phone,
	                          @RequestParam(name="email")String email,
	                          @RequestParam(name="img") MultipartFile file) throws Exception{
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
		FileUtils.writeByteArrayToFile(new File(classPath+user.getUsername()+".jpg"),file.getBytes());
		userService.updateUser(user,password,description,phone,email);
	}

	@RequestMapping("/userdetail")
	public User userdetail(){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		user.setRoles(null);
		user.setOrders(null);

		//return user;
		return new User(user.getId(),user.getUsername(),user.getPassword(),user.getDescription(),user.getPhone(),user.getEmail());
	}
	@RequestMapping("/addcart")
	public void addcart(HttpServletRequest httpServletRequest,
	                    @RequestParam(name="id") Long id){
		HttpSession session=httpServletRequest.getSession();
		List<CartBook> cart=(List<CartBook>)session.getAttribute("cart")	;
		if (cart==null) {
			cart=new ArrayList<>();
		}
		for (CartBook b:cart){
			if (b.getId().equals(id)){
				b.setCount(b.getCount()+1);
				session.setAttribute("cart",cart);
				return;
			}
		}
		Book book= bookService.showOneById(id);
		CartBook cbook=new CartBook(book.getId(),book.getName(),book.getWriter(),book.getPrice(),1);
		cart.add(cbook);
		session.setAttribute("cart",cart);
	}

	@RequestMapping("/removecart")
	public void removecart(HttpServletRequest httpServletRequest,
	                    @RequestParam(name="id") Long id) {
		HttpSession session = httpServletRequest.getSession();
		List<CartBook> cart = (List<CartBook>) session.getAttribute("cart");
		for (CartBook b : cart) {
			if (b.getId().equals(id)) {
				cart.remove(b);
				session.setAttribute("cart", cart);
				return;
			}
		}
	}

	@RequestMapping("/showcart")
	public List<CartBook> showcart(HttpServletRequest httpServletRequest){
		HttpSession session=httpServletRequest.getSession();
		return (List<CartBook>)session.getAttribute("cart")	;
	}

	@RequestMapping("/addimg")
	public void addimg(@RequestParam(name="img")MultipartFile file) throws Exception{
		String classPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/static/image/";
		FileUtils.writeByteArrayToFile(new File(classPath+file.getOriginalFilename()),file.getBytes());
	}
}
