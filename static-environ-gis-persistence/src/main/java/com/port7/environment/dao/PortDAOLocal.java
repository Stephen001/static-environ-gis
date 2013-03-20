package com.port7.environment.dao;

import java.util.List;

import javax.ejb.Local;

import com.port7.environment.model.Port;
import com.port7.environment.persistence.PortJPA;

@Local
public interface PortDAOLocal {
	public List<PortJPA> searchByNameOrAlias(final String term);
	
	public void addAlias(final String alias, final PortJPA port);

	public void removeAlias(final String alias, final PortJPA portJPA);

	public void updateMetadata(final String oldName, final Port port);

	public void delete(final PortJPA byName);
	
	public List<String> getAliases(final PortJPA port);
}
