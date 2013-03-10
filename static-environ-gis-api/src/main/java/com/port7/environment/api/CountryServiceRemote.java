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

import com.port7.environment.model.Country;

/**
 * A service for getting {@link Country}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Remote
public interface CountryServiceRemote {
	/**
	 * Gets a {@link Country} by it's English country name for this country, as defined by ISO 3166-1.
	 * 
	 * @param englishName English country name for this country, as defined by ISO 3166-1.
	 * @return A Country.
	 */
	public Country getByEnglishName(final String englishName);
	
	/**
	 * Checks if a given port is within the bounds of the provided country.
	 * 
	 * @param portName The port to check.
	 * @param countryName The country to check for.
	 * @return True if the port is within the bounds of the named country, false otherwise.
	 */
	public boolean isPortInCountry(final String portName, final String countryName);
	
	/**
	 * Gets all country names and aliases.
	 * 
	 * @return All country names and aliases.
	 */
	public List<String> getCountryNamesAndAliases();
	
	/**
	 * Adds an alias to the specified country.
	 * 
	 * @param alias The alias for the country.
	 * @param country The country to alias.
	 */
	public void addAlias(final String alias, final Country country);
	
	/**
	 * Removes an alias to the specified country.
	 * 
	 * @param alias The alias for the country.
	 * @param country The country to alias.
	 */
	public void removeAlias(final String alias, final Country country);
	
	/**
	 * Updates country information. In order to map english name corrections, the old name
	 * must be provided. If you are just updating other information, the existing name can
	 * be provided.
	 * 
	 * @param oldEnglishName The old english name for this country.
	 * @param country The updated country information.
	 */
	public void updateCountryInfo(final String oldEnglishName, final Country country);
	
	/**
	 * Deletes a country (and all aliases) from the system.
	 * 
	 * @param country The country to delete.
	 */
	public void deleteCountry(final Country country);
}
