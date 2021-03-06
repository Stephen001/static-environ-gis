/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.model;

import com.vividsolutions.jts.geom.Geometry;

/**
 * An immutable geographical representation of a area on the globe. Areas are very general, and typically do not encompass
 * internationally defined standards. Sub-classes may potentially be mutable.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public final  class AreaImpl implements Area {
	private static final long serialVersionUID = 3250883859948824868L;
	private final Geometry shape;
	private final String englishName;
	private final AreaType type;

	/**
	 * Constructs an Area with the provided parameters.
	 * 
	 * @param englishName English country name for this area.
	 * @param location The shape of the area, in global coordinates.
	 * @param type The type of area this is.
	 */
	public AreaImpl(final String englishName, final Geometry shape, final AreaType type) {
		this.englishName 	= englishName;
		this.shape 			= shape;
		this.type 			= type;
	}
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.model.Area#getGeometry()
	 */
	@Override
	public final Geometry getGeometry() {
		return shape;
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.model.Area#getEnglishName()
	 */
	@Override
	public final String getEnglishName() {
		return englishName;
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.model.Area#getType()
	 */
	@Override
	public AreaType getType() {
		return type;
	}
}
