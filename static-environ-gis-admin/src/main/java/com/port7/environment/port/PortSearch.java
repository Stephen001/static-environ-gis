package com.port7.environment.port;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.port7.environment.api.PortServiceRemote;
import com.port7.environment.model.Port;

@ManagedBean
@RequestScoped
public class PortSearch {
	private String term;
	
	@EJB
	private PortServiceRemote service;
	
	public List<Port> search(AjaxBehaviorEvent event) {
		return service.searchPortByNameOrAlias(term == null ? "" : term);
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
