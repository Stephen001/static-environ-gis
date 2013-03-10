/*
 * Copyright (c) Port7 2012. 
 * All rights reserved. 
 * No part of this program may be reproduced, translated or transmitted, 
 * in any form or by any means, electronic, mechanical, photocopying, 
 * recording or otherwise, or stored in any retrieval system of any nature, 
 * without written permission of the copyright holder. 
 */
package com.port7.environment.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Alias names for Ports.
 * 
 * @author Stephen Badger [stephen@port7.dk]
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "port-from-alias", query = "SELECT p.port FROM PortAliasJPA p WHERE UPPER(p.name) = :name"),
		@NamedQuery(name = "existing-alias-for-port", query = "SELECT p FROM PortAliasJPA p WHERE UPPER(p.name) = :name AND p.port = :port"),
		@NamedQuery(name = "aliases-from-port", query = "SELECT p FROM PortAliasJPA p WHERE p.port = :port"),
		@NamedQuery(name = "all-port-aliases", query = "SELECT p.name FROM PortAliasJPA p")
})
public class PortAliasJPA extends AbstractEntityJPA {
	@Column(nullable = false, unique = true)
	private String name;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private PortJPA port;
	
	/**
	 * Gets the name to alias to.
	 * 
	 * @return The name to alias to.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the port this is mapped to.
	 * 
	 * @return The port this is mapped to.
	 */
	public PortJPA getPort() {
		return port;
	}
	
	/**
	 * Sets the name to alias to.
	 * 
	 * @param name The name to alias to.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the port this is mapped to.
	 * 
	 * @param port The port this is mapped to.
	 */
	public void setPort(PortJPA port) {
		this.port = port;
	}
}
