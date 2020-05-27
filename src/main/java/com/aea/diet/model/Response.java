package com.aea.diet.model;

public class Response {
	
	private String message;
	private int code;
	
	public Response(String message, int code) {
		super();
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

}
