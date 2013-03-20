package com.port7.environment.dao;

import java.util.List;

import javax.ejb.Local;

import com.port7.environment.model.AreaType;

@Local
public interface AreaTypeDAOLocal {
	public void create(String type);
	
	public void delete(String type);
	
	public long count(AreaType type);
	
	public List<AreaType> getAll();
}
