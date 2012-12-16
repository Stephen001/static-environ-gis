/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import com.vividsolutions.jts.geom.Point;

/**
 * An immutable serialisable implementation of a {@link Port}, intended to be used across remote interfaces.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
final class PortImpl implements Port {
	private static final long serialVersionUID = -4920071550270946372L;
	private final String englishName;
	private final Point  location;
	
	/**
	 * Constructs a Port with the provided parameters.
	 * 
	 * @param englishName English country name for this port.
	 * @param location The location of the port on the globe.
	 */
	PortImpl(final String englishName, final Point location) {
		this.englishName = englishName;
		this.location	 = location;
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.Port#getEnglishName()
	 */
	@Override
	public String getEnglishName() {
		return englishName;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.Port#getLocation()
	 */
	@Override
	public Point getLocation() {
		return location;
	}
}
