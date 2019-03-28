package com.example.demo.dao;

import com.example.demo.domain.SysRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysRoleRepository extends MongoRepository<SysRole,String> {
	SysRole findByIdEquals(String id);
}
