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

import com.vividsolutions.jts.geom.Point;

/**
 * A geographical representation of a port on the globe.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public interface Port extends Serializable, Location {
	/**
	 * Returns the English port name for this port. This will be unique to each port instance.
	 * 
	 * @return The English port name for this port.
	 */
	public String getEnglishName();
	
	/**
	 * Gets the point that represents this Port's location on the globe.
	 * 
	 * @return The point that represents this Port's location on the globe.
	 */
	public Point getGeometry();
}
