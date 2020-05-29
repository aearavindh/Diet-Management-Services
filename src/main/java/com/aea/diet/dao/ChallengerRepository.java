package com.aea.diet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aea.diet.model.Challenger;

public interface ChallengerRepository extends JpaRepository<Challenger, Integer> {
	
	@Query("from Challenger where status='Pending'")
	List<Challenger> findByStatus();

}
