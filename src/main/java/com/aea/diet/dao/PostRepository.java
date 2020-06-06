package com.aea.diet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByTo(String to);

}
