package com.port7.environment.dao;

import javax.ejb.Local;

import com.port7.environment.model.Country;
import com.port7.environment.persistence.CountryJPA;

@Local
public interface CountryDAOLocal {
	public void addAlias(final String alias, final CountryJPA country);

	public void removeAlias(final String alias, final CountryJPA countryJPA);

	public void updateMetadata(final String oldName, final Country country);

	public void delete(final CountryJPA byName);
}
