package com.port7.environment.areatype;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.port7.environment.model.AreaType;

@ManagedBean(name = "AreaTypeConverter")
public class AreaTypeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return new AreaType(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((AreaType) arg2).toString();
	}

}
