package com.port7.environment.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
