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

import com.port7.environment.model.Port;
import com.port7.environment.model.PortMapperLocal;

/**
 * A service for getting {@link Port}s manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class PortService implements PortServiceRemote {
	@EJB
	private PortMapperLocal mapper;
	
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
}
