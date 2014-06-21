package com.awesomeware.shipping.environment.dao;

import java.util.List;

import javax.ejb.Local;

import com.awesomeware.shipping.environment.model.Port;
import com.awesomeware.shipping.environment.persistence.PortJPA;

@Local
public interface PortDAOLocal {
	public List<PortJPA> searchByNameOrAlias(final String term);
	
	public boolean addAlias(final String alias, final PortJPA port);

	public boolean removeAlias(final String alias, final PortJPA portJPA);

	public void updateMetadata(final String oldName, final Port port);

	public List<String> delete(final PortJPA byName);
	
	public List<String> getAliases(final PortJPA port);
}
