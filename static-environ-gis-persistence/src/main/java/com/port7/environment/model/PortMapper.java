/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.model;

import java.util.Locale;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.port7.environment.persistence.PortJPA;

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

	@Override
	public PortJPA getByName(String englishName) {
		TypedQuery<PortJPA> query = manager.createNamedQuery("port-by-alias", PortJPA.class);
		query.setParameter("name", englishName.toUpperCase(Locale.ENGLISH));
		return query.getSingleResult();
	}
}
