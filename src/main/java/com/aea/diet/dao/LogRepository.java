package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aea.diet.model.DailyLog;

public interface LogRepository extends JpaRepository<DailyLog, Integer> {
	
	@Query("from DailyLog where email = ?1 and date = ?2")
	DailyLog findByEmail(String email, String date);

}
