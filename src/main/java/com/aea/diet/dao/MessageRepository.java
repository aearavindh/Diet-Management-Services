package com.aea.diet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findByTo(String to);

}
