package com.port7.environment.dao;

import javax.ejb.Local;

import com.port7.environment.model.Area;
import com.port7.environment.persistence.AreaJPA;

@Local
public interface AreaDAOLocal {
	public void addAlias(final String alias, final AreaJPA area);

	public void removeAlias(final String alias, final AreaJPA areaJPA);

	public void updateMetadata(final String oldName, final Area area);

	public void delete(final AreaJPA byName);
}
