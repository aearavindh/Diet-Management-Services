package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
	
	@Id 
	@GeneratedValue
	@JsonIgnore
	private int id;
	private String date;
	private String to;
	private String fromUser;
	private String message;
	@Lob 
	private byte[] file;
	
	public Post() {
		super();
	}

	public Post(String date, String to, String fromUser, String message, byte[] file) {
		super();
		this.date = date;
		this.to = to;
		this.fromUser = fromUser;
		this.message = message;
		this.file = file;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getTo() {
		return to;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getMessage() {
		return message;
	}

	public byte[] getFile() {
		return file;
	}

}
