/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.api;

import java.util.List;

import javax.ejb.Remote;

import com.port7.environment.model.Area;

/**
 * A service for getting {@link Area}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Remote
public interface AreaServiceRemote {
	/**
	 * Gets a {@link Area} by it's English area name for this area.
	 * 
	 * @param englishName English area name for this area.
	 * @return A Area.
	 */
	public Area getByEnglishName(final String englishName);
	
	/**
	 * Checks if a given port is within the bounds of the provided area.
	 * 
	 * @param portName The port to check.
	 * @param areaName The area to check for.
	 * @return True if the port is within the bounds of the named area, false otherwise.
	 */
	public boolean isPortInArea(final String portName, final String areaName);
	
	/**
	 * Gets all area names and aliases.
	 * 
	 * @return All area names and aliases.
	 */
	public List<String> getAreaNamesAndAliases();
	
	/**
	 * Adds an alias to the specified area.
	 * 
	 * @param alias The alias for the area.
	 * @param area The area to alias.
	 */
	public void addAlias(final String alias, final Area area);
	
	/**
	 * Removes an alias from the specified area.
	 * 
	 * @param alias The alias for the area.
	 * @param area The area to alias.
	 */
	public void removeAlias(final String alias, final Area area);
	
	/**
	 * Updates area information. In order to map english name corrections, the old name
	 * must be provided. If you are just updating other information, the existing name can
	 * be provided.
	 * 
	 * @param oldEnglishName The old english name for this area.
	 * @param area The updated area information.
	 */
	public void updateAreaInfo(final String oldEnglishName, final Area area);
	
	/**
	 * Deletes an area (and all aliases) from the system.
	 * 
	 * @param area The area to delete.
	 */
	public void deleteArea(final Area area);
}
