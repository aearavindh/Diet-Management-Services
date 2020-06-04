package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class DailyLog {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;
	private String email;
	private String date;
	private String breakfast;
	private String lunch;
	private String dinner;
	private String fruits;
	private String vegetables;
	private String workouts;
	
	public DailyLog() {
		super();
	}

	public DailyLog(String email, String date, String breakfast, String lunch, String dinner, String fruits,
			String vegetables, String workouts) {
		super();
		this.email = email;
		this.date = date;
		this.breakfast = breakfast;
		this.lunch = lunch;
		this.dinner = dinner;
		this.fruits = fruits;
		this.vegetables = vegetables;
		this.workouts = workouts;
	}
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getDate() {
		return date;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public String getLunch() {
		return lunch;
	}

	public String getDinner() {
		return dinner;
	}

	public String getFruits() {
		return fruits;
	}

	public String getVegetables() {
		return vegetables;
	}

	public String getWorkouts() {
		return workouts;
	}
	

}
