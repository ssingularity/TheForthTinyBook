package com.example.demo.web;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class BookController {
	@Autowired
	BookService bookService;
	@RequestMapping("/search")
	List<Book> search(@RequestParam(name="query") String query){
		return bookService.search(query);
	}

	@RequestMapping("/")
	List<Book> showAll(){
		return bookService.showAll();
	}

	@RequestMapping("/bookdetail")
	Book showOne(@RequestParam(name="id") Long id){
		return bookService.showOneById(id);
	}
}
