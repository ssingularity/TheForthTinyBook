package com.example.demo;

import com.example.demo.service.OrderService;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import javax.websocket.OnMessage;

@SpringBootApplication
@EnableCaching
public class DemoApplication extends SpringBootServletInitializer {
	@Bean
	RmiProxyFactoryBean service() {
		RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
		rmiProxyFactory.setServiceUrl("rmi://localhost:1099/OrderService");
		rmiProxyFactory.setServiceInterface(OrderService.class);
		return rmiProxyFactory;
	}

	public static void main(String[] args) {
		//System.setProperty("es.set.netty.runtime.available.processors","false");
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

//	@Bean
//	CommandLineRunner demo(RelationUserRepository relationUserRepository) {
//		return args -> {
//		    System.out.println("start neo4j");
//			relationUserRepository.deleteAll();
//			RelationUser userA = new RelationUser("User A");
//			RelationUser userB = new RelationUser("User B");
//			RelationUser userC = new RelationUser("User C");
//
//			relationUserRepository.save(userA);
//			relationUserRepository.save(userB);
//			relationUserRepository.save(userC);
//
//			userA.follow(userB);
//			userA.follow(userC);
//			userB.follow(userC);
//			userC.follow(userB);
//
//			relationUserRepository.save(userA);
//			relationUserRepository.save(userB);
//			relationUserRepository.save(userC);
//		};
//	}
}
