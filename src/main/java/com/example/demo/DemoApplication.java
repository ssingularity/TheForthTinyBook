package com.example.demo;

import com.example.demo.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
@EnableCaching
public class DemoApplication {
	@Bean
	RmiProxyFactoryBean service() {
		RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
		rmiProxyFactory.setServiceUrl("rmi://localhost:1099/OrderService");
		rmiProxyFactory.setServiceInterface(OrderService.class);
		return rmiProxyFactory;
	}

	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors","false");
		SpringApplication.run(DemoApplication.class, args);
	}
}
