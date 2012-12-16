/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import javax.ejb.Local;

import com.port7.environment.persistence.PortJPA;

/**
 * Maps between {@link PortJPA} and {@link Port}.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Local
public interface PortMapperLocal {

	/**
	 * Maps to DTO.
	 * 
	 * @param c The JPA entity.
	 * @return The DTO.
	 */
	public Port mapToDTO(final PortJPA c);

	/**
	 * Maps from DTO.
	 * 
	 * @param Port The DTO.
	 * @return The JPA entity.
	 */
	public PortJPA mapFromDTO(final Port Port);
	
	/**
	 * Gets a Port by it's name.
	 * 
	 * @param englishName The name of the Port.
	 * @return The entity.
	 */
	public PortJPA getByName(final String englishName);
}