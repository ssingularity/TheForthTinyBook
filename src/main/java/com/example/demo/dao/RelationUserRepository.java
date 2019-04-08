package com.example.demo.dao;

import com.example.demo.domain.RelationUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RelationUserRepository extends Neo4jRepository<RelationUser, Long> {
    RelationUser findRelationUserByName(String name);
}
