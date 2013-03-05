/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.port7.environment.dao.PortDAOLocal;
import com.port7.environment.model.Port;
import com.port7.environment.model.PortMapperLocal;
import com.port7.environment.persistence.PortJPA;

/**
 * A service for getting {@link Port}s manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class PortService implements PortServiceRemote {
	@EJB
	private PortMapperLocal mapper;
	
	@EJB
	private PortDAOLocal dao;
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#getByEnglishName(java.lang.String)
	 */
	@Override
	public Port getByEnglishName(final String englishName) {
		return mapper.mapToDTO(mapper.getByName(englishName));
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#getPortNamesAndAliases()
	 */
	@Override
	public List<String> getPortNamesAndAliases() {
		return mapper.getNamesAndAliases();
	}

	@Override
	public List<Port> searchPortByNameOrAlias(String term) {
		List<Port> results = new ArrayList<Port>();
		for (PortJPA port : dao.searchByNameOrAlias(term)) {
			results.add(mapper.mapToDTO(port));
		}
		return results;
	}
}
