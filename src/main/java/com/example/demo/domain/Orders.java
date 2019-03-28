package com.example.demo.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
public class Orders implements Serializable {
    @Id
	private String id;

	private String userId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String bookName;
	private String bookId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private Integer count;
	private Integer totalPrice;
	private Date time;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getTime() {
		return time;
	}

	public Orders(String bookName,  String bookId, Integer count, Integer totalPrice, Date time) {
		this.bookName = bookName;
		this.bookId = bookId;
		this.count = count;
		this.totalPrice = totalPrice;
		this.time = time;
	}

	public Orders() {

	}

	public void setTime(Date time) {
		this.time = time;
	}
}
