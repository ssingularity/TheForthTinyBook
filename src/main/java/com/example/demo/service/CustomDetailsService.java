package com.example.demo.service;

import com.example.demo.dao.SysUserRepository;
import com.example.demo.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDetailsService implements UserDetailsService{
	@Autowired
	SysUserRepository sysUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username){
		SysUser user=sysUserRepository.findByUsername(username);
		if(user==null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		System.out.println(user.getUsername());
		return user;
	}
}
