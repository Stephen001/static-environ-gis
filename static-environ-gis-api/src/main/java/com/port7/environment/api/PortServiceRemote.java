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
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import com.port7.environment.model.Port;

/**
 * A service for getting {@link Port}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Remote
public interface PortServiceRemote {
	/**
	 * Gets a {@link Port} by it's English Port name for this Port.
	 * 
	 * @param englishName English Port name for this Port.
	 * @return A Port.
	 */
	public Port getByEnglishName(final String englishName);
	
	/**
	 * Searches for Ports by their name or alias, partial matches are permitted.
	 * 
	 * @param term The search term to use.
	 * @return A (potentially empty) list of results.
	 */
	public List<Port> searchPortByNameOrAlias(final String term);
	
	/**
	 * Gets all port names and aliases.
	 * 
	 * @return All port names and aliases.
	 */
	public List<String> getPortNamesAndAliases();
	
	/**
	 * Adds an alias to the specified port.
	 * 
	 * @param alias The alias for the port.
	 * @param port The port to alias.
	 */
	public void addAlias(final String alias, final Port port);
	
	/**
	 * Removes an alias to the specified port.
	 * 
	 * @param alias The alias for the port.
	 * @param port The port to alias.
	 */
	public void removeAlias(final String alias, final Port port);
	
	/**
	 * Updates port information. In order to map english name corrections, the old name
	 * must be provided. If you are just updating other information, the existing name can
	 * be provided.
	 * 
	 * @param oldEnglishName The old english name for this port.
	 * @param port The updated port information.
	 */
	public void updatePortInfo(final String oldEnglishName, final Port port);
	
	/**
	 * Deletes a port (and all aliases) from the system.
	 * 
	 * @param port The port to delete.
	 */
	public void deletePort(final Port port);
	
	/**
	 * Gets the list of aliases associated with the given port.
	 * 
	 * @param port The port to fetch aliases for.
	 * @return The list of aliases for this port.
	 */
	public List<String> getAliases(Port port);
	
	/**
	 * Gets the full set of ports, with their associated aliases. Please note this is
	 * potentially a very large data set and so may be very memory-intensive.
	 * 
	 * @return The full set of ports, with their aliases.
	 */
	public Map<Port, Set<String>> getPortsAndAliases();
}
