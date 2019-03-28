package com.example.demo.domain;

import java.io.Serializable;

public class CartBook implements Serializable{
	private String id;
	private String name;
	private String writer;
	private Integer price;
	private Integer count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public CartBook(String id, String name, String writer, Integer price, Integer count) {
		this.id = id;
		this.name = name;
		this.writer = writer;
		this.price = price;
		this.count = count;
	}

	public CartBook() {
	}
}
