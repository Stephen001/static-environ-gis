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

import com.port7.environment.persistence.PortJPA;
import com.vividsolutions.jts.geom.Point;

/**
 * Maps between {@link PortJPA} JPA entities and {@link Port} DTOs.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class PortMapper implements PortMapperLocal {
	@PersistenceContext(unitName = "static-environ-gis")
	private EntityManager manager;
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#mapToDTO(com.port7.environment.persistence.Port)
	 */
	@Override
	public Port mapToDTO(final PortJPA c) {
		PortImpl dto = new PortImpl(c.getEnglishName(), 
									c.getLocation());
		return dto;
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#mapFromDTO(com.port7.environment.model.Port)
	 */
	@Override
	public PortJPA mapFromDTO(Port port) {
		return getByName(port.getEnglishName());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#getByName(java.lang.String)
	 */
	@Override
	public PortJPA getByName(String englishName) {
		TypedQuery<PortJPA> query = manager.createNamedQuery("port-by-name", PortJPA.class);
		query.setParameter("englishName", englishName.toUpperCase(Locale.ENGLISH));
		return query.getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#getNamesAndAliases()
	 */
	@Override
	public List<String> getNamesAndAliases() {
		TypedQuery<String> query = manager.createNamedQuery("all-port-aliases", String.class);
		return new ArrayList<String>(query.getResultList());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#newPort(java.lang.String, com.vividsolutions.jts.geom.Point)
	 */
	@Override
	public Port newPort(String englishName, Point point) {
		return new PortImpl(englishName, point);
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.model.PortMapperLocal#getPortsAndAliases()
	 */
	@Override
	public Map<Port, Set<String>> getPortsAndAliases() {
		Map<Port, Set<String>> results = new HashMap<Port, Set<String>>();
		TypedQuery<PortJPA> query = manager.createNamedQuery("all-ports", PortJPA.class);
		TypedQuery<String> aliasesQuery = manager.createNamedQuery("port-aliases", String.class);
		for (PortJPA port : query.getResultList()) {
			aliasesQuery.setParameter("port", port);
			Set<String> aliases = new HashSet<>(aliasesQuery.getResultList());
			results.put(mapToDTO(port), aliases);
		}
		return results;
	}
}
