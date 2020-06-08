package com.aea.diet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.DietGroup;

public interface GroupRepository extends JpaRepository<DietGroup, Integer> {
	
	DietGroup findByName(String name);

	List<DietGroup> findByBatchId(int batchId);

}
