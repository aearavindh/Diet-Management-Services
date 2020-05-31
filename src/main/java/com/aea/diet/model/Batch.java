package com.aea.diet.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity 
public class Batch {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(mappedBy="batch")
	private List<DietGroup> dietGroups;
	
	public Batch() {
		super();
	}

	public Batch(String name) {
		super();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
