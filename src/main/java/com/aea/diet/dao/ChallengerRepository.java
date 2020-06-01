package com.aea.diet.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.aea.diet.model.Challenger;

@Transactional
public interface ChallengerRepository extends JpaRepository<Challenger, Integer> {
	
	@Query("from Challenger where status='Pending'")
	List<Challenger> findByStatus();
	
	@Modifying
	@Query("update Challenger c set c.status = ?2 where c.email = ?1")
	int updateStatusByEmail(String email, String newStatus);

}
