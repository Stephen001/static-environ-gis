/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

/**
 * A JPA implementation of a port. Queries will mostly use the englishName to identify data.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "all-ports", query = "SELECT p FROM PortJPA p"), 
		@NamedQuery(name = "port-by-name", query = "SELECT p FROM PortJPA p WHERE UPPER(p.englishName) = :englishName"),
		@NamedQuery(name = "port-search-by-name", query = "SELECT DISTINCT p FROM PortAliasJPA a RIGHT JOIN a.port p WHERE UPPER(p.englishName) LIKE :term OR UPPER(a.name) LIKE :term")
})
public class PortJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String englishName;
	@Column(nullable = false)
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Point location;
	
	/**
	 * Returns the English port name for this port. This will be unique to each port instance.
	 * 
	 * @return The English port name for this port.
	 */
	public String getEnglishName() {
		return englishName;
	}
	
	/**
	 * Gets the location of this port, as a point on the globe.
	 * 
	 * @return The location of this port, as a point on the globe.
	 */
	public Point getLocation() {
		return location;
	}
	
	/**
	 * Sets the English port name for this port. This will be unique to each port instance.
	 * 
	 * @param englishName The English port name for this port.
	 */
	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}
	
	/**
	 * Sets the location of this port, as a point on the globe.
	 * 
	 * @param location The location of this port, as a point on the globe.
	 */
	public void setLocation(final Point location) {
		this.location = location;
	}
}
