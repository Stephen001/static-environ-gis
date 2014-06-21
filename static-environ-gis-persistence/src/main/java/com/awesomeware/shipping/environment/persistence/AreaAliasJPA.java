/*
 * Copyright (c) Area7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Alias names for Areas.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "area-from-alias", query = "SELECT p.area FROM AreaAliasJPA p WHERE UPPER(p.name) = :name"),
		@NamedQuery(name = "existing-alias-for-area", query = "SELECT p FROM AreaAliasJPA p WHERE UPPER(p.name) = :name AND p.area = :area"),
		@NamedQuery(name = "aliases-from-area", query = "SELECT p FROM AreaAliasJPA p WHERE p.area = :area"), 
		@NamedQuery(name = "all-area-aliases", query = "SELECT p.name FROM AreaAliasJPA p"),
		@NamedQuery(name = "all-area-aliases-full", query = "SELECT p FROM AreaAliasJPA p"),
		@NamedQuery(name = "area-aliases", query = "SELECT p.name FROM AreaAliasJPA p WHERE p.area = :area")
})
public class AreaAliasJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String name;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private AreaJPA area;
	
	/**
	 * Gets the name to alias to.
	 * 
	 * @return The name to alias to.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the area this is mapped to.
	 * 
	 * @return The area this is mapped to.
	 */
	public AreaJPA getArea() {
		return area;
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
	 * Sets the area this is mapped to.
	 * 
	 * @param area The area this is mapped to.
	 */
	public void setArea(AreaJPA area) {
		this.area = area;
	}
}
