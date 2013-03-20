package com.port7.environment.areatype;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.port7.environment.dao.AreaTypeDAOLocal;

@ManagedBean(name = "AreaTypeCrud")
@RequestScoped
public class AreaTypeCrud {
	@EJB
	private AreaTypeDAOLocal dao;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void create() {
		dao.create(type);
	}
	
	public void delete() {
		dao.delete(type);
	}
}
