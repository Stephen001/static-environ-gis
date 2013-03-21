/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import com.port7.environment.persistence.AreaJPA;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Maps between {@link AreaJPA} and {@link Area}.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Local
public interface AreaMapperLocal {

	/**
	 * Maps to DTO.
	 * 
	 * @param c The JPA entity.
	 * @return The DTO.
	 */
	public Area mapToDTO(final AreaJPA c);

	/**
	 * Maps from DTO.
	 * 
	 * @param area The DTO.
	 * @return The JPA entity.
	 */
	public AreaJPA mapFromDTO(final Area area);
	
	/**
	 * Gets an Area by it's name.
	 * 
	 * @param englishName The name of the area.
	 * @return The entity.
	 */
	public AreaJPA getByName(final String englishName);
	
	/**
	 * Gets all area names and aliases.
	 * 
	 * @return All area names and aliases.
	 */
	public List<String> getNamesAndAliases();
	
	/**
	 * Gets all areas and their aliases. Please note, this result set is likely to be very
	 * large, and potentially consume a lot of memory.
	 * 
	 * @return A map of areas to aliases.
	 */
	public Map<Area, Set<String>> getAreasAndAliases();

	public Area newArea(String englishName, Polygon read, AreaType type);
}