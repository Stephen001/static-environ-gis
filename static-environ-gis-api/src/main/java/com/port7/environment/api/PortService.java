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

	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#addAlias(java.lang.String, com.port7.environment.model.Port)
	 */
	@Override
	public void addAlias(String alias, Port port) {
		PortJPA portJPA = mapper.mapFromDTO(port);
		if (portJPA != null) {
			dao.addAlias(alias, portJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#removeAlias(java.lang.String, com.port7.environment.model.Port)
	 */
	@Override
	public void removeAlias(String alias, Port port) {
		PortJPA portJPA = mapper.mapFromDTO(port);
		if (portJPA != null) {
			dao.removeAlias(alias, portJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#updatePortInfo(java.lang.String, com.port7.environment.model.Port)
	 */
	@Override
	public void updatePortInfo(String oldEnglishName, Port port) {
		dao.updateMetadata(oldEnglishName, port);
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#deletePort(com.port7.environment.model.Port)
	 */
	@Override
	public void deletePort(final Port port) {
		dao.delete(mapper.mapFromDTO(port));
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.PortServiceRemote#getAliases(com.port7.environment.model.Port)
	 */
	@Override
	public List<String> getAliases(Port port) {
		return dao.getAliases(mapper.mapFromDTO(port));
	}
}
