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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.persistence.AreaJPA;
import com.vividsolutions.jts.geom.Polygon;

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
		TypedQuery<AreaJPA> query = manager.createNamedQuery("area-by-name", AreaJPA.class);
		query.setParameter("englishName", englishName.toUpperCase(Locale.ENGLISH));
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

	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#newArea(java.lang.String, com.vividsolutions.jts.geom.Polygon, com.port7.environment.model.AreaType)
	 */
	@Override
	public Area newArea(String englishName, Polygon read, AreaType type) {
		return new AreaImpl(englishName, read, type);
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.AreaMapperLocal#getAreasAndAliases()
	 */
	@Override
	public Map<Area, Set<String>> getAreasAndAliases() {
		Map<Area, Set<String>> results = new HashMap<>();
		TypedQuery<AreaJPA> areaQuery = manager.createNamedQuery("all-areas", AreaJPA.class);
		TypedQuery<String> aliasesQuery = manager.createNamedQuery("area-aliases", String.class);
		for (AreaJPA a : areaQuery.getResultList()) {
			aliasesQuery.setParameter("area", a);
			Set<String> aliases = new HashSet<>(aliasesQuery.getResultList());
			results.put(mapToDTO(a), aliases);
		}
		return results;
	}
}
