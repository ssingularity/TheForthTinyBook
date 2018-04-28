package com.example.demo.web;

import com.example.demo.domain.Orders;
import com.example.demo.domain.SysUser;
import com.example.demo.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping("/logup")
	public String logup(@RequestParam(name="username")String username,
	                  @RequestParam(name="password")String password,
	                  @RequestParam(name="description")String description,
	                  @RequestParam(name="phone")String phone,
	                  @RequestParam(name="email")String email){
		if (userService.isExisted(username))	return "该用户名已存在";
		else {
			userService.addUser(username,password,description,phone,email);
			return "注册成功";
		}
	}

	@RequestMapping("/makeorder")
	public String makeorder(@RequestParam(name="id")Long id,
							@RequestParam(name="count")Integer count){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		if (userService.makeOrder(user,id,count)) return "订单下单成功";
		else return "库存不足";
	}

	@RequestMapping("/showorder")
	public List<Orders> showorder(){
		SecurityContext securityContext= SecurityContextHolder.getContext();
		Authentication authentication=securityContext.getAuthentication();
		SysUser user=(SysUser) authentication.getPrincipal();
		return userService.showOrder(user);
	}
}
