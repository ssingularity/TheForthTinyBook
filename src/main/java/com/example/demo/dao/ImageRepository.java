package com.example.demo.dao;

import com.example.demo.domain.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ImageRepository extends MongoRepository<Image,String> {
	Image findByName(String name);
}
