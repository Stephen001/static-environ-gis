package com.port7.environment.port;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.port7.environment.api.PortServiceRemote;
import com.port7.environment.model.Port;

@ManagedBean(name = "PortSearch")
@RequestScoped
public class PortSearch {
	private String term;
	private List<Port> results = new ArrayList<>();
	
	@EJB
	private PortServiceRemote service;
	
	public void search(AjaxBehaviorEvent event) {
		setResults(service.searchPortByNameOrAlias(term == null ? "" : term));
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

	public void setResults(List<Port> results) {
		this.results = results;
	}
}
