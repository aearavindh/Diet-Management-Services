package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Message {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;
	private String date;
	private String to;
	private String fromUser;
	private String message;
	
	public Message() {
		super();
	}

	public Message(String date, String to, String fromUser, String message) {
		super();
		this.date = date;
		this.to = to;
		this.fromUser = fromUser;
		this.message = message;
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

	public String getFrom() {
		return fromUser;
	}

	public String getMessage() {
		return message;
	}
	

}
