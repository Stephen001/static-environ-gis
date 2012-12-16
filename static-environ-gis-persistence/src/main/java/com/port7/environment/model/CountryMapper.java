/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.persistence.CountryJPA;

/**
 * Maps between {@link CountryJPA} JPA entities and {@link Country} DTOs.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class CountryMapper implements CountryMapperLocal {
	@PersistenceContext(unitName = "static-environ-gis")
	private EntityManager manager;
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.CountryMapperLocal#mapToDTO(com.port7.environment.persistence.Country)
	 */
	@Override
	public Country mapToDTO(final CountryJPA c) {
		CountryImpl dto = new CountryImpl(c.getEnglishName(), 
										  c.getTwoCountryCode(),
										  c.getThreeCountryCode(),
										  c.getLandMassShape());
		return dto;
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.CountryMapperLocal#mapFromDTO(com.port7.environment.model.Country)
	 */
	@Override
	public CountryJPA mapFromDTO(Country country) {
		return getByName(country.getEnglishName());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.CountryMapperLocal#getByName(java.lang.String)
	 */
	@Override
	public CountryJPA getByName(String englishName) {
		TypedQuery<CountryJPA> query = manager.createNamedQuery("country-by-alias", CountryJPA.class);
		query.setParameter("name", englishName.toUpperCase(Locale.ENGLISH));
		return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.CountryMapperLocal#getNamesAndAliases()
	 */
	@Override
	public List<String> getNamesAndAliases() {
		TypedQuery<String> query = manager.createNamedQuery("all-country-aliases", String.class);
		return new ArrayList<String>(query.getResultList());
	}
}
