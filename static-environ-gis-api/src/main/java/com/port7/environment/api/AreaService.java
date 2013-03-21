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
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.port7.environment.dao.AreaDAOLocal;
import com.port7.environment.model.Area;
import com.port7.environment.model.AreaMapperLocal;
import com.port7.environment.model.PortMapperLocal;
import com.port7.environment.persistence.AreaJPA;
import com.port7.environment.persistence.PortJPA;

/**
 * A service for getting {@link Area}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class AreaService implements AreaServiceRemote {
	@EJB
	private AreaMapperLocal areaMapper;
	@EJB
	private PortMapperLocal portMapper;
	@EJB
	private AreaDAOLocal areaDAO;
	
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#getByEnglishName(java.lang.String)
	 */
	@Override
	public Area getByEnglishName(final String englishName) {
		return areaMapper.mapToDTO(areaMapper.getByName(englishName));
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#isPortInArea(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPortInArea(String portName, String areaName) {
		AreaJPA area = areaMapper.getByName(areaName);
		PortJPA port = portMapper.getByName(portName);
		return port.getLocation().within(area.getLandMassShape());
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#getAreaNamesAndAliases()
	 */
	@Override
	public List<String> getAreaNamesAndAliases() {
		return areaMapper.getNamesAndAliases();
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#addAlias(java.lang.String, com.port7.environment.model.Area)
	 */
	@Override
	public void addAlias(String alias, Area area) {
		AreaJPA areaJPA = areaMapper.getByName(area.getEnglishName());
		if (areaJPA != null) {
			areaDAO.addAlias(alias, areaJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#removeAlias(java.lang.String, com.port7.environment.model.Area)
	 */
	@Override
	public void removeAlias(String alias, Area area) {
		AreaJPA areaJPA = areaMapper.getByName(area.getEnglishName());
		if (areaJPA != null) {
			areaDAO.removeAlias(alias, areaJPA);
		}
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#updateAreaInfo(java.lang.String, com.port7.environment.model.Area)
	 */
	@Override
	public void updateAreaInfo(String oldEnglishName, Area area) {
		areaDAO.updateMetadata(oldEnglishName, area);
	}
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#deleteArea(com.port7.environment.model.Area)
	 */
	@Override
	public void deleteArea(final Area area) {
		areaDAO.delete(areaMapper.getByName(area.getEnglishName()));
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#searchAreaByNameOrAlias(java.lang.String)
	 */
	@Override
	public List<Area> searchAreaByNameOrAlias(String term) {
		List<Area> results = new ArrayList<Area>();
		for (AreaJPA port : areaDAO.searchByNameOrAlias(term)) {
			results.add(areaMapper.mapToDTO(port));
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#getAliases(com.port7.environment.model.Area)
	 */
	@Override
	public List<String> getAliases(Area area) {
		return areaDAO.getAliases(areaMapper.mapFromDTO(area));
	}

	/* (non-Javadoc)
	 * @see com.port7.environment.api.AreaServiceRemote#getAreasAndAliases()
	 */
	@Override
	public Map<Area, Set<String>> getAreasAndAliases() {
		return areaMapper.getAreasAndAliases();
	}
}
