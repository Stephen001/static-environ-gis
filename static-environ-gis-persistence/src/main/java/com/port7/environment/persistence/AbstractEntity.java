/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A super-class for all persistable entities. These entities shall probably each form a separate table.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * Gets the unique identifier for this entity.
	 * 
	 * @return The unique identifier for this entity.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the unique identifier for this entity.
	 * 
	 * @param id The unique identifier for this entity.
	 */
	public void setId(final long id) {
		this.id = id;
	}
}
