package com.example.demo.dao;


import com.example.demo.domain.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends ElasticsearchRepository<Blog, String> {
    List<Blog> findAllByAuthorNameLikeOrTitleLikeOrContentLike(String authorName, String Title, String content);
    Blog findBlogById(String id);
}
