package com.port7.environment.area;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.port7.environment.api.AreaServiceRemote;
import com.port7.environment.model.Area;

@ManagedBean(name = "AreaSearch")
@SessionScoped
public class AreaSearch {
	private String term;
	private List<Area> results = new ArrayList<>();
	
	@EJB
	private AreaServiceRemote service;
	
	public void search() {
		results = service.searchAreaByNameOrAlias(term == null ? "" : term);
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<Area> getResults() {
		return results;
	}
}
