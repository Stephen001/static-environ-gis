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

import com.vividsolutions.jts.geom.Geometry;

/**
 * A geographical representation of a location or area on the globe.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
public interface Location extends Serializable {
	public Geometry getGeometry();
}
