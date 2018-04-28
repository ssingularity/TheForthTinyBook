package com.example.demo.dao;

import com.example.demo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface BookRepository extends JpaRepository<Book,Long>{
	List<Book> findByNameLikeOrWriterLike(String name,String writer);
	List<Book> findByPriceBetween(Integer start,Integer end);
	List<Book> findByPublishTimeBetween(Date start,Date end);
	List<Book> findByName(String name);
	Book findByIdEquals(Long id);
}
