/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.model;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Geometry;

/**
 * A geographical representation of an area on the globe. Areas are very general, and typically do not encompass
 * internationally defined standards.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public interface Area extends Location, Serializable {
	/**
	 * Returns the English port name for this area. This will be unique to each area.
	 * 
	 * @return The English port name for this area.
	 */
	public String getEnglishName();
	
	/**
	 * Gets the polygon that represents this Area.
	 * 
	 * @return The polygon representing this Area.
	 */
	public Geometry getGeometry();
	
	/**
	 * Gets the area type associated with this area.
	 * 
	 * @return The type of area this is.
	 */
	public AreaType getType();
}
