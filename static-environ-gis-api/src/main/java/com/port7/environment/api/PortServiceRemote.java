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

import javax.ejb.Remote;

import com.port7.environment.model.Port;

/**
 * A service for getting {@link Port}s and manipulating them.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Remote
public interface PortServiceRemote {
	/**
	 * Gets a {@link Port} by it's English Port name for this Port.
	 * 
	 * @param englishName English Port name for this Port.
	 * @return A Port.
	 */
	public Port getByEnglishName(final String englishName);
	
	/**
	 * Gets all port names and aliases.
	 * 
	 * @return All port names and aliases.
	 */
	public List<String> getPortNamesAndAliases();
}
