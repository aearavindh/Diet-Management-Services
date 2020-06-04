package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MonthlyChart {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;
	private String email;
	private String month;
	private String weight;
	private String height;
	private String chest;
	private String waist;
	private String shoulders;
	private String biceps;
	private String forearm;
	private String leg;
	private String thighs;
	
	public MonthlyChart() {
		super();
	}

	public MonthlyChart(String email, String month, String weight, String height, String chest, String waist,
			String shoulders, String biceps, String forearm, String leg, String thighs) {
		super();
		this.email = email;
		this.month = month;
		this.weight = weight;
		this.height = height;
		this.chest = chest;
		this.waist = waist;
		this.shoulders = shoulders;
		this.biceps = biceps;
		this.forearm = forearm;
		this.leg = leg;
		this.thighs = thighs;
	}
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getMonth() {
		return month;
	}

	public String getWeight() {
		return weight;
	}

	public String getHeight() {
		return height;
	}

	public String getChest() {
		return chest;
	}

	public String getWaist() {
		return waist;
	}

	public String getShoulders() {
		return shoulders;
	}

	public String getBiceps() {
		return biceps;
	}

	public String getForearm() {
		return forearm;
	}

	public String getLeg() {
		return leg;
	}

	public String getThighs() {
		return thighs;
	}
	

}
