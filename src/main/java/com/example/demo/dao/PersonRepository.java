package com.example.demo.dao;

import java.util.*;
import com.example.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long> {
	List<Person> findByAddress(String name);
	Person findByNameAndAddress(String name,String address);
	@Query("select p from Person p where p.name=:name and p.address=:address")
	Person withNameAndAddressQuery(@Param("name")String name,@Param("address") String address);
}
