package com.awesomeware.shipping.environment.areatype;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.awesomeware.shipping.environment.dao.AreaTypeDAOLocal;
import com.awesomeware.shipping.environment.model.AreaType;

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
