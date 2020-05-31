package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.DietGroup;

public interface GroupRepository extends JpaRepository<DietGroup, Integer> {

}
