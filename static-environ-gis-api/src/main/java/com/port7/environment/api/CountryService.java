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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.port7.environment.dao.CountryDAOLocal;
import com.port7.environment.model.Country;
import com.port7.environment.model.CountryMapperLocal;
import com.port7.environment.model.PortMapperLocal;
import com.port7.environment.persistence.CountryJPA;
import com.port7.environment.persistence.PortJPA;

/**
 * A service for getting {@link Country}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class CountryService implements CountryServiceRemote {
	@EJB
	private CountryMapperLocal countryMapper;
	@EJB
	private PortMapperLocal portMapper;
	@EJB
	private CountryDAOLocal countryDAO;
	
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#getByEnglishName(java.lang.String)
	 */
	@Override
	public Country getByEnglishName(final String englishName) {
		return countryMapper.mapToDTO(countryMapper.getByName(englishName));
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#isPortInCountry(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPortInCountry(String portName, String countryName) {
		CountryJPA country = countryMapper.getByName(countryName);
		PortJPA port = portMapper.getByName(portName);
		return port.getLocation().within(country.getLandMassShape());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#getCountryNamesAndAliases()
	 */
	@Override
	public List<String> getCountryNamesAndAliases() {
		return countryMapper.getNamesAndAliases();
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#addAlias(java.lang.String, com.port7.environment.model.Country)
	 */
	@Override
	public void addAlias(String alias, Country country) {
		CountryJPA countryJPA = countryMapper.getByName(country.getEnglishName());
		if (countryJPA != null) {
			countryDAO.addAlias(alias, countryJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#removeAlias(java.lang.String, com.port7.environment.model.Country)
	 */
	@Override
	public void removeAlias(String alias, Country country) {
		CountryJPA countryJPA = countryMapper.getByName(country.getEnglishName());
		if (countryJPA != null) {
			countryDAO.removeAlias(alias, countryJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#updateCountryInfo(java.lang.String, com.port7.environment.model.Country)
	 */
	@Override
	public void updateCountryInfo(String oldEnglishName, Country country) {
		countryDAO.updateMetadata(oldEnglishName, country);
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#deleteCountry(com.port7.environment.model.Country)
	 */
	@Override
	public void deleteCountry(final Country country) {
		countryDAO.delete(countryMapper.getByName(country.getEnglishName()));
	}
}
