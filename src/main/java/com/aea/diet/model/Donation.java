package com.aea.diet.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Donation {
	
	@Id
	@GeneratedValue
	private int id;
	private Date donatedDate;
	private String donor;
	private int amount;
	
	public Donation() {
		super();
	}

	public Donation(Date donatedDate, String donor, int amount) {
		super();
		this.donatedDate = donatedDate;
		this.donor = donor;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public Date getDonatedDate() {
		return donatedDate;
	}

	public String getDonor() {
		return donor;
	}

	public int getAmount() {
		return amount;
	}
	

}
