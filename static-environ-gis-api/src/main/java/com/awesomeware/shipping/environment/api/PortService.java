/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.awesomeware.shipping.environment.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.awesomeware.shipping.environment.dao.PortDAOLocal;
import com.awesomeware.shipping.environment.model.Port;
import com.awesomeware.shipping.environment.model.PortMapperLocal;
import com.awesomeware.shipping.environment.model.event.DataChangeType;
import com.awesomeware.shipping.environment.model.event.PortDataChangeEvent;
import com.awesomeware.shipping.environment.persistence.PortJPA;

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
	
	@Inject
	private Event<PortDataChangeEvent> event;
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#getByEnglishName(java.lang.String)
	 */
	@Override
	public Port getByEnglishName(final String englishName) {
		return mapper.mapToDTO(mapper.getByName(englishName));
	}
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#getPortNamesAndAliases()
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
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#addAlias(java.lang.String, com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public void addAlias(String alias, Port port) {
		PortJPA portJPA = mapper.mapFromDTO(port);
		if (portJPA != null) {
			if (dao.addAlias(alias, portJPA)) {
				event.fire(new PortDataChangeEvent(alias, port, DataChangeType.ADD_ALIAS));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#removeAlias(java.lang.String, com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public void removeAlias(String alias, Port port) {
		PortJPA portJPA = mapper.mapFromDTO(port);
		if (portJPA != null) {
			if (dao.removeAlias(alias, portJPA)) {
				event.fire(new PortDataChangeEvent(alias, port, DataChangeType.REMOVE_ALIAS));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#updatePortInfo(java.lang.String, com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public void updatePortInfo(String oldEnglishName, Port port) {
		dao.updateMetadata(oldEnglishName, port);
	}
	
	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#deletePort(com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public void deletePort(final Port port) {
		for (String alias : dao.delete(mapper.mapFromDTO(port))) {
			event.fire(new PortDataChangeEvent(alias, port, DataChangeType.REMOVE_ENTITY));
		}
		event.fire(new PortDataChangeEvent(port.getEnglishName(), port, DataChangeType.REMOVE_ENTITY));
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#getAliases(com.awesomeware.shipping.environment.model.Port)
	 */
	@Override
	public List<String> getAliases(Port port) {
		return dao.getAliases(mapper.mapFromDTO(port));
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#getPortsAndAliases()
	 */
	@Override
	public Map<Port, Set<String>> getPortsAndAliases() {
		return mapper.getPortsAndAliases();
	}

	/* (non-Javadoc)
	 * @see com.awesomeware.shipping.environment.api.PortServiceRemote#updatePortInfo(java.util.Map)
	 */
	@Override
	public void updatePortInfo(Map<String, Port> ports) {
		for (Entry<String, Port> entry : ports.entrySet()) {
			updatePortInfo(entry.getKey(), entry.getValue());
		}
	}
}
