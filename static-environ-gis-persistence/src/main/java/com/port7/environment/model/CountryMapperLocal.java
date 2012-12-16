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

import javax.ejb.Local;

import com.port7.environment.persistence.CountryJPA;

/**
 * Maps between {@link CountryJPA} and {@link Country}.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Local
public interface CountryMapperLocal {

	/**
	 * Maps to DTO.
	 * 
	 * @param c The JPA entity.
	 * @return The DTO.
	 */
	public Country mapToDTO(final CountryJPA c);

	/**
	 * Maps from DTO.
	 * 
	 * @param country The DTO.
	 * @return The JPA entity.
	 */
	public CountryJPA mapFromDTO(final Country country);
	
	/**
	 * Gets a Country by it's name.
	 * 
	 * @param englishName The name of the country.
	 * @return The entity.
	 */
	public CountryJPA getByName(final String englishName);
	
	/**
	 * Gets all country names and aliases.
	 * 
	 * @return All country names and aliases.
	 */
	public List<String> getNamesAndAliases();
}