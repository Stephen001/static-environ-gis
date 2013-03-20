package com.port7.environment.areatype;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.port7.environment.dao.AreaTypeDAOLocal;
import com.port7.environment.model.AreaType;

@ManagedBean(name = "AreaTypeList")
@RequestScoped
public class AreaTypeList {
	@EJB
	private AreaTypeDAOLocal dao;

	public List<AreaType> listTypes() {
		return dao.getAll();
	}
	
	public String count(AreaType type) {
		return Long.toString(dao.count(type));
	}
}
