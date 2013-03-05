package com.port7.environment.dao;

import java.util.List;

import javax.ejb.Local;

import com.port7.environment.persistence.PortJPA;

@Local
public interface PortDAOLocal {
	public List<PortJPA> searchByNameOrAlias(final String term);
}
