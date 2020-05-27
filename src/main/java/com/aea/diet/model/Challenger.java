package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Challenger {
	
	@Id
	@GeneratedValue
	private int id;
	private String program;
	private String name;
	private String age;
	private String gender;
	private String mobile;
	private String email;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pinCode;
	private String height;
	private String weight;
	private String bmi;
	private String reason;
	private String medicalConditions;
	private String dietaryRestrictions;
	private String dietaryCustom;
	private String referralCode;
	private String pregnantStatus;
	private String status;
	
	public Challenger() {
		super();
	}

	public Challenger(String program, String name, String age, String gender, String mobile, String email,
			String address, String city, String state, String country, String pinCode, String height, String weight,
			String bmi, String reason, String medicalConditions, String dietaryRestrictions, String dietaryCustom,
			String referralCode, String pregnantStatus, String status) {
		super();
		this.program = program;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pinCode = pinCode;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.reason = reason;
		this.medicalConditions = medicalConditions;
		this.dietaryRestrictions = dietaryRestrictions;
		this.dietaryCustom = dietaryCustom;
		this.referralCode = referralCode;
		this.pregnantStatus = pregnantStatus;
		this.status = status;
	}

	public String getProgram() {
		return program;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getPinCode() {
		return pinCode;
	}

	public String getHeight() {
		return height;
	}

	public String getWeight() {
		return weight;
	}

	public String getBmi() {
		return bmi;
	}

	public String getReason() {
		return reason;
	}

	public String getMedicalConditions() {
		return medicalConditions;
	}

	public String getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public String getDietaryCustom() {
		return dietaryCustom;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public String getPregnantStatus() {
		return pregnantStatus;
	}

	public String getStatus() {
		return status;
	}
	
	
}
