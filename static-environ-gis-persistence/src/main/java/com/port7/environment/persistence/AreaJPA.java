/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

/**
 * A JPA implementation of an area. Queries will mostly use the englishName to identify data.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "all-areas", query = "SELECT a FROM AreaJPA a"),
		@NamedQuery(name = "area-by-name", query = "SELECT c FROM AreaJPA c WHERE UPPER(c.englishName) = :englishName"),
		@NamedQuery(name = "area-search-by-name", query = "SELECT DISTINCT p FROM AreaAliasJPA a RIGHT JOIN a.area p WHERE UPPER(p.englishName) LIKE :term OR UPPER(a.name) LIKE :term")
})
public class AreaJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String  englishName;
	@Column(nullable = false)
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Geometry landMassShape;
	@ManyToOne(fetch = FetchType.EAGER)
	@Index(name = "areatype")
	private AreaTypeJPA type;
	
	/**
	 * Returns the English area name for this area. This will be unique to each area instance.
	 * 
	 * @return The English area name for this area.
	 */
	public String getEnglishName() {
		return englishName;
	}
	
	/**
	 * Gets the polygon that represents this area's land mass.
	 * 
	 * @return The polygon representing this area's land mass.
	 */
	public Geometry getLandMassShape() {
		return landMassShape;
	}
	
	/**
	 * Gets the defined type for this area.
	 * 
	 * @return The type for the area.
	 */
	public AreaTypeJPA getType() {
		return type;
	}
	
	/**
	 * Sets the English area name for this area. This will be unique to each area instance.
	 * 
	 * @param englishName The English area name for this area.
	 */
	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}

	/**
	 * Sets the polygon that represents this area's land mass.
	 * 
	 * @param landMassShape The polygon representing this area's land mass.
	 */
	public void setLandMassShape(final Geometry landMassShape) {
		this.landMassShape = landMassShape;
	}

	/**
	 * Sets the defined type for this area.
	 * 
	 * @param type The type for the area.
	 */
	public void setType(AreaTypeJPA type) {
		this.type = type;
	}
}
