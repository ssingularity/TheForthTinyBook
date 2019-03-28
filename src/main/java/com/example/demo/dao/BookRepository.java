package com.example.demo.dao;

import com.example.demo.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;

public interface BookRepository extends MongoRepository<Book,String> {
	List<Book> findByNameLikeOrWriterLike(String name,String writer);
	List<Book> findByPriceBetween(Integer start,Integer end);
	List<Book> findByPublishTimeBetween(Date start,Date end);
	List<Book> findByName(String name);
	Book findByIdEquals(String id);
}
