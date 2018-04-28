package com.example.demo.web;

import com.example.demo.dao.PersonRepository;
import com.example.demo.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DateController {
	@Autowired
	PersonRepository personRepository;
	@RequestMapping("/save")
	public Person save(String name,String address,Integer age){
		Person p=personRepository.save(new Person(name,age,address));
		return p;
	}

	@RequestMapping("/q1")
	public List<Person> q1(String address){
		List<Person> people=personRepository.findByAddress(address);
		return people;
	}
}
