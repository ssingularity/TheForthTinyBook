package com.example.demo.service;

import com.example.demo.dao.BlogRepository;
import com.example.demo.domain.Blog;
import com.example.demo.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;

    public Blog add(Blog blog){
        SecurityContext securityContext= SecurityContextHolder.getContext();
        Authentication authentication=securityContext.getAuthentication();
        SysUser user=(SysUser) authentication.getPrincipal();
        blog.setAuthor(user.getId());
        blog.setAuthorName(user.getUsername());
        return blogRepository.save(blog);
    }

    public List<Blog> find(String query){
        query = "*" + query + "*";
        return blogRepository.findAllByAuthorNameLikeOrTitleLikeOrContentLike(query, query, query);
    }

    public Blog findOne(String id){
        return  blogRepository.findBlogById(id);
    }

}
