/*
 * Copyright (c) Country7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Alias names for Countries.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "country-from-alias", query = "SELECT p.country FROM CountryAliasJPA p WHERE UPPER(p.name) = :name"),
		@NamedQuery(name = "existing-alias-for-country", query = "SELECT p FROM CountryAliasJPA p WHERE UPPER(p.name) = :name AND p.country = :country"),
		@NamedQuery(name = "aliases-from-country", query = "SELECT p FROM CountryAliasJPA p WHERE p.country = :country"),
		@NamedQuery(name = "all-country-aliases", query = "SELECT p.name FROM CountryAliasJPA p")
})
public class CountryAliasJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String name;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private CountryJPA country;
	
	/**
	 * Gets the name to alias to.
	 * 
	 * @return The name to alias to.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the country this is mapped to.
	 * 
	 * @return The country this is mapped to.
	 */
	public CountryJPA getCountry() {
		return country;
	}
	
	/**
	 * Sets the name to alias to.
	 * 
	 * @param name The name to alias to.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the country this is mapped to.
	 * 
	 * @param country The country this is mapped to.
	 */
	public void setCountry(CountryJPA country) {
		this.country = country;
	}
}
