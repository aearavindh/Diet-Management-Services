package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class DietGroup {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;
	private String name;
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Batch batch;
	
	public DietGroup() {
		super();
	}

	public DietGroup(String name, Batch batch) {
		super();
		this.name = name;
		this.batch = batch;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Batch getBatch() {
		return batch;
	}

}
