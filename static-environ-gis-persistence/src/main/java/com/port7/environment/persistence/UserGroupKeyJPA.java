package com.port7.environment.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserGroupKeyJPA implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String name;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
