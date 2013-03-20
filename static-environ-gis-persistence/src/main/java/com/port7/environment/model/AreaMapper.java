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

import com.port7.environment.persistence.AreaJPA;

/**
 * Maps between {@link AreaJPA} JPA entities and {@link Area} DTOs.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class AreaMapper implements AreaMapperLocal {
	@PersistenceContext(unitName = "static-environ-gis")
	private EntityManager manager;
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#mapToDTO(com.port7.environment.persistence.Area)
	 */
	@Override
	public Area mapToDTO(final AreaJPA c) {
		AreaImpl dto = new AreaImpl(c.getEnglishName(),c.getLandMassShape(), new AreaType(c.getType().getName()));
		return dto;
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#mapFromDTO(com.port7.environment.model.Area)
	 */
	@Override
	public AreaJPA mapFromDTO(Area area) {
		return getByName(area.getEnglishName());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#getByName(java.lang.String)
	 */
	@Override
	public AreaJPA getByName(String englishName) {
		TypedQuery<AreaJPA> query = manager.createNamedQuery("area-by-alias", AreaJPA.class);
		query.setParameter("name", englishName.toUpperCase(Locale.ENGLISH));
		return query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#getNamesAndAliases()
	 */
	@Override
	public List<String> getNamesAndAliases() {
		TypedQuery<String> query = manager.createNamedQuery("all-area-aliases", String.class);
		return new ArrayList<String>(query.getResultList());
	}
}
