package com.port7.environment.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "user")
@SessionScoped
public class UserBean {
	
	public String getName() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return context.getRemoteUser();
	}
}
