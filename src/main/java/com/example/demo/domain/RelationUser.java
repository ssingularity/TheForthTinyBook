//package com.example.demo.domain;
//
//import org.neo4j.ogm.annotation.GraphId;
//import org.neo4j.ogm.annotation.NodeEntity;
//import org.neo4j.ogm.annotation.Relationship;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@NodeEntity
//public class RelationUser {
//    @GraphId
//    Long id;
//    String name;
//
//    @Relationship(type = "关注")
//    public Set<RelationUser> followers;
//
//    public void follow(RelationUser user){
//        if (followers == null) followers = new HashSet<>();
//        followers.add(user);
//    }
//    public RelationUser(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Set<RelationUser> getFollowers() {
//        return followers;
//    }
//
//    public void setFollowers(Set<RelationUser> followers) {
//        this.followers = followers;
//    }
//}
