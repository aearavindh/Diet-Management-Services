package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.Batch;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

}
