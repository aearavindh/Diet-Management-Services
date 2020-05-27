package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.Challenger;

public interface ChallengerRepository extends JpaRepository<Challenger, Integer> {

}
