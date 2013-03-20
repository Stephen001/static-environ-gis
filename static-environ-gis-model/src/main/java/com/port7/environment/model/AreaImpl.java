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
 * An immutable geographical representation of a area on the globe. Areas are very general, and typically do not encompass
 * internationally defined standards. Sub-classes may potentially be mutable.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public class AreaImpl implements Area {
	private static final long serialVersionUID = 3250883859948824868L;
	private final Polygon shape;
	private final String englishName;

	/**
	 * Constructs an Area with the provided parameters.
	 * 
	 * @param englishName English country name for this area.
	 * @param location The shape of the area, in global coordinates.
	 */
	public AreaImpl(final String englishName, final Polygon shape) {
		this.englishName 	= englishName;
		this.shape 			= shape;
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.Area#getGeometry()
	 */
	@Override
	public final Polygon getGeometry() {
		return shape;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Area#getEnglishName()
	 */
	@Override
	public final String getEnglishName() {
		return englishName;
	}
}
