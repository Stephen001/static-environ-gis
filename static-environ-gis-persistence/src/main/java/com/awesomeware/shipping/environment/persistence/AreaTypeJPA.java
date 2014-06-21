package com.awesomeware.shipping.environment.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "count-area-type", query = "SELECT COUNT(a) FROM AreaJPA a WHERE a.type = :type"),
	@NamedQuery(name = "all-area-types", query = "SELECT t FROM AreaTypeJPA t")
})
public class AreaTypeJPA {
	@Id
	@Column(nullable = false, unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
