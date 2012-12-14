/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.api;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.port7.environment.model.Country;
import com.port7.environment.model.CountryMapperLocal;

/**
 * A service for getting {@link Country}s manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Stateless
public class CountryService implements CountryServiceRemote {
	@EJB
	private CountryMapperLocal mapper;
	
	/* (non-Javadoc)
	 * @see com.port7.environment.api.CountryServiceRemote#getByEnglishName(java.lang.String)
	 */
	@Override
	public Country getByEnglishName(final String englishName) {
		return mapper.mapToDTO(mapper.getByName(englishName));
	}
}
