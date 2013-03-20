/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import com.vividsolutions.jts.geom.Polygon;

/**
 * An immutable serialisable implementation of a {@link Country}, intended to be used across remote interfaces.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public final class CountryImpl implements Country {
	private static final long serialVersionUID = -2710918101788553618L;
	private final String  englishName;
	private final String  twoCharCountryCode;
	private final String  threeCharCountryCode;
	private final Polygon landMassShape;
	
	/**
	 * Constructs a Country with the provided parameters.
	 * 
	 * @param englishName English country name for this country, as defined by ISO 3166-1.
	 * @param twoCharCountryCode Two character country code for this country, as defined by ISO 3166-1 alpha-2.
	 * @param threeCharCountryCode Three character country code for this country, as defined by ISO 3166-1 alpha-3.
	 * @param landMassShape The polygon that represents this Country's land mass.
	 */
	public CountryImpl(final String englishName,
				final String twoCharCountryCode,
				final String threeCharCountryCode,
				final Polygon landMassShape) {
		this.englishName 			= englishName;
		this.twoCharCountryCode 	= twoCharCountryCode;
		this.threeCharCountryCode	= threeCharCountryCode;
		this.landMassShape 			= landMassShape;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Country#getEnglishName()
	 */
	@Override
	public String getEnglishName() {
		return englishName;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Country#getTwoCharCountryCode()
	 */
	@Override
	public String getTwoCharCountryCode() {
		return twoCharCountryCode;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Country#getThreeCharCountryCode()
	 */
	@Override
	public String getThreeCharCountryCode() {
		return threeCharCountryCode;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Country#getLandMassShape()
	 */
	@Override
	public Polygon getGeometry() {
		return landMassShape;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof Country) {
			Country c = (Country) o;
			return c.getEnglishName().equals(englishName);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 17 + englishName.hashCode(); // Shifts it along, so we don't weirdly collide with regular strings.
	}
}
