package com.example.demo.dao;

import com.example.demo.domain.SysUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SysUserRepository extends MongoRepository<SysUser,String> {
	SysUser findByUsername(String username);
	SysUser findByIdEquals(String id);
}
