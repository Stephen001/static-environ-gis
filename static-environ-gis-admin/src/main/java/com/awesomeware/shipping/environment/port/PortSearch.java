package com.awesomeware.shipping.environment.port;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.awesomeware.shipping.environment.api.PortServiceRemote;
import com.awesomeware.shipping.environment.model.Port;

@ManagedBean(name = "PortSearch")
@SessionScoped
public class PortSearch {
	private String term;
	private List<Port> results = new ArrayList<>();
	
	@EJB
	private PortServiceRemote service;
	
	public void search() {
		results = service.searchPortByNameOrAlias(term == null ? "" : term);
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<Port> getResults() {
		return results;
	}
}
