/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Polygon;

/**
 * A geographical representation of a country on the globe. All names and codes within this interface comply with ISO 3166.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public interface Country extends Serializable, Location {
	/**
	 * Returns the English country name for this country, as defined by ISO 3166-1. This will be unique to each country instance.
	 * 
	 * @return The English country name for this country.
	 */
	public String getEnglishName();
	
	/**
	 * Returns the two character country code for this country, as defined by ISO 3166-1 alpha-2.
	 * 
	 * @return The two character country code for this country.
	 */
	public String getTwoCharCountryCode();
	
	/**
	 * Returns the three character country code for this country, as defined by ISO 3166-1 alpha-3.
	 * 
	 * @return The three character country code for this country.
	 */
	public String getThreeCharCountryCode();
	
	/**
	 * Gets the polygon that represents this Country's land mass.
	 * 
	 * @return The polygon representing this Country's land mass.
	 */
	public Polygon getGeometry();
}
