package com.example.demo.config;

import com.example.demo.service.CustomDetailsService;
import com.example.demo.service.MyPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Bean
	UserDetailsService custromUserService(){
		return new CustomDetailsService();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(custromUserService()).passwordEncoder(new MyPasswordEncoder());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().permitAll()
		.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.csrf()
				.disable();
	}
}
