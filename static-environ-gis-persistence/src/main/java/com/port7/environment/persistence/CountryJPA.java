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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Polygon;

/**
 * A JPA implementation of a country. Queries will mostly use the englishName to identify data.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries(
		@NamedQuery(name = "country-by-name", query = "SELECT c FROM CountryJPA c WHERE UPPER(c.englishName) = :englishName")
		)
public class CountryJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String  englishName;
	@Column(nullable = false, unique = true)
	private String  twoCountryCode;
	@Column(nullable = false, unique = true)
	private String  threeCountryCode;
	@Column(nullable = false)
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Polygon landMassShape;
	
	/**
	 * Returns the English country name for this country, as defined by ISO 3166-1. This will be unique to each country instance.
	 * 
	 * @return The English country name for this country.
	 */
	public String getEnglishName() {
		return englishName;
	}
	
	/**
	 * Returns the two character country code for this country, as defined by ISO 3166-1 alpha-2.
	 * 
	 * @return The two character country code for this country.
	 */
	public String getTwoCountryCode() {
		return twoCountryCode;
	}
	
	/**
	 * Returns the three character country code for this country, as defined by ISO 3166-1 alpha-3.
	 * 
	 * @return The three character country code for this country.
	 */
	public String getThreeCountryCode() {
		return threeCountryCode;
	}
	
	/**
	 * Gets the polygon that represents this Country's land mass.
	 * 
	 * @return The polygon representing this Country's land mass.
	 */
	public Polygon getLandMassShape() {
		return landMassShape;
	}
	
	/**
	 * Sets the English country name for this country, as defined by ISO 3166-1. This will be unique to each country instance.
	 * 
	 * @param englishName The English country name for this country.
	 */
	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}
	
	/**
	 * Sets the two character country code for this country, as defined by ISO 3166-1 alpha-2.
	 * 
	 * @param twoCountryCode The two character country code for this country.
	 */
	public void setTwoCountryCode(final String twoCountryCode) {
		this.twoCountryCode = twoCountryCode;
	}
	
	/**
	 * Sets the three character country code for this country, as defined by ISO 3166-1 alpha-3.
	 * 
	 * @param threeCountryCode The three character country code for this country.
	 */
	public void setThreeCountryCode(final String threeCountryCode) {
		this.threeCountryCode = threeCountryCode;
	}
	
	/**
	 * Sets the polygon that represents this Country's land mass.
	 * 
	 * @param landMassShape The polygon representing this Country's land mass.
	 */
	public void setLandMassShape(final Polygon landMassShape) {
		this.landMassShape = landMassShape;
	}
}
