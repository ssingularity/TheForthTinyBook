package com.example.demo.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "image")
public class Image {
	@Id
	String name;
	byte[] image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Image(String name, byte[] image) {
		this.name = name;
		this.image = image;
	}

}
