package com.example.demo.service;

import com.example.demo.dao.BookRepository;
import com.example.demo.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
	private boolean isNumber(String s){
		for(int i=0;i<s.length();++i){
			if(!Character.isDigit(s.charAt(i))) return false;
		}
		return true;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Book addBook(String name, String writer, Integer price, Integer storage, String description, Date publishTime){
		Book book=new Book(name,writer,price,storage,description,publishTime);
		return bookRepository.save(book);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void removeBook(Long id){
		bookRepository.deleteById(id);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Book showOneById(Long id){
		return bookRepository.findByIdEquals(id);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Book updateBook(Long id,String name, String writer, Integer price, Integer storage, String description, Date publishTime){
		Book book=showOneById(id);
		book.setName(name);
		book.setDescription(description);
		book.setWriter(writer);
		book.setPrice(price);
		book.setStorage(storage);
		book.setPublishTime(publishTime);
		return bookRepository.save(book);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public List<Book> search(String query){
		int index=query.indexOf('~');
		if(index==-1){
			query='%'+query+'%';
			return bookRepository.findByNameLikeOrWriterLike(query,query);
		}
		else {
			String start=query.substring(0,index);
			String end=query.substring(index+1,query.length());
			if(isNumber(start)&&isNumber(end)){
				Integer istart= Integer.parseInt(start);
				Integer iend=Integer.parseInt((end));
				return bookRepository.findByPriceBetween(istart,iend);
			}
			else{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date dstart=simpleDateFormat.parse(start);
					Date dend=simpleDateFormat.parse(end);
					return bookRepository.findByPublishTimeBetween(dstart,dend);
				}
				catch (ParseException ex){
					return null;
				}
			}
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Book> showAll(){
		return bookRepository.findAll();
	}
}
