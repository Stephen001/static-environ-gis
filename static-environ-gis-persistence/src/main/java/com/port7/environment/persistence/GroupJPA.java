package com.port7.environment.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class GroupJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
