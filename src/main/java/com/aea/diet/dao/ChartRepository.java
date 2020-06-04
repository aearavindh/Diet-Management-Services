package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aea.diet.model.MonthlyChart;

public interface ChartRepository extends JpaRepository<MonthlyChart, Integer> {
	
	@Query("from MonthlyChart where email = ?1 and month = ?2")
	MonthlyChart findByEmail(String email, String month);

}
