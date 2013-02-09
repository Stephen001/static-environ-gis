package com.port7.environment.persistence;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserJPAGroupJPA {
	@EmbeddedId
	private UserGroupKeyJPA id;
	
	public UserGroupKeyJPA getId() {
		return id;
	}
	
	public void setId(UserGroupKeyJPA id) {
		this.id = id;
	}
}
