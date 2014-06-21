/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import com.awesomeware.shipping.environment.persistence.PortJPA;
import com.vividsolutions.jts.geom.Point;

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
	 * @param port The DTO.
	 * @return The JPA entity.
	 */
	public PortJPA mapFromDTO(final Port port);
	
	/**
	 * Gets a Port by it's name.
	 * 
	 * @param englishName The name of the Port.
	 * @return The entity.
	 */
	public PortJPA getByName(final String englishName);
	
	/**
	 * Gets all port names and aliases.
	 * 
	 * @return All port names and aliases.
	 */
	public List<String> getNamesAndAliases();
	
	/**
	 * Creates a new serialisable port.
	 * 
	 * @param englishName The name of the port.
	 * @param point The position on the globe where the port is.
	 * @return A new serialisable port.
	 */
	public Port newPort(String englishName, Point point);

	/**
	 * Gets the full set of ports, with their associated aliases. Please note this is
	 * potentially a very large data set and so may be very memory-intensive.
	 * 
	 * @return The full set of ports, with their aliases.
	 */
	public Map<Port, Set<String>> getPortsAndAliases();
}