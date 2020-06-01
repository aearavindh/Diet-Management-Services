package com.aea.diet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aea.diet.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

}
