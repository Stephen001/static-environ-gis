package com.awesomeware.shipping.environment.dao;

import java.util.List;

import javax.ejb.Local;

import com.awesomeware.shipping.environment.model.Area;
import com.awesomeware.shipping.environment.persistence.AreaJPA;

@Local
public interface AreaDAOLocal {
	public boolean addAlias(final String alias, final AreaJPA area);

	public boolean removeAlias(final String alias, final AreaJPA areaJPA);

	public void updateMetadata(final String oldName, final Area area);

	public List<String> delete(final AreaJPA byName);

	public List<AreaJPA> searchByNameOrAlias(String term);

	public List<String> getAliases(AreaJPA mapFromDTO);
}
