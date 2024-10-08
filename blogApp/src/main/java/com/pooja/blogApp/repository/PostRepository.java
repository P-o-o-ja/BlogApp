package com.pooja.blogApp.repository;

import com.pooja.blogApp.entity.Category;
import com.pooja.blogApp.entity.Post;
import com.pooja.blogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByPostTitleContaining(String keyword);
}
