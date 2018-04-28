package com.example.demo.dao;

import com.example.demo.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole,Long>{
	SysRole findByIdEquals(Long id);
}
