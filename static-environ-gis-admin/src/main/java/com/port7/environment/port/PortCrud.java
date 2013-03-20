package com.port7.environment.port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.port7.environment.api.PortServiceRemote;
import com.port7.environment.model.Port;
import com.port7.environment.model.PortMapperLocal;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

@ManagedBean(name = "PortCrud")
@RequestScoped
public class PortCrud {
	private String englishName;
	private String coordinate;
	private String aliases;
	
	@EJB
	private PortMapperLocal portMapper;
	
	@EJB
	private PortServiceRemote service;
	
	public String getEnglishName() {
		return englishName;
	}
	
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public String getCoordinate() {
		return coordinate;
	}
	
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	public void persist() throws ParseException {
		WKTReader reader = new WKTReader();
		Port port = portMapper.newPort(englishName, (Point) reader.read(coordinate));
		service.updatePortInfo(englishName, port);
		List<String> oldAliases = service.getAliases(port);
		List<String> newAliases = new ArrayList<>();
		if (!aliases.isEmpty()) {
			newAliases = Arrays.asList(aliases.split(", "));
		}
		List<String> removed = new ArrayList<>(oldAliases);
		removed.removeAll(newAliases);
		for (String a : removed) {
			service.removeAlias(a, port);
		}
		List<String> added = new ArrayList<>(newAliases);
		added.removeAll(oldAliases);
		for (String a : added) {
			service.addAlias(a, port);
		}
	}
	
	public String getCoordinateString(Point point) {
		WKTWriter writer = new WKTWriter();
		return writer.write(point);
	}
	
	public String getAliasString(Port port) {
		StringBuilder builder = new StringBuilder();
		for (String alias : service.getAliases(port)) {
			builder.append(alias);
			builder.append(", ");
		}
		String end = builder.toString();
		if (end.endsWith(", ")) {
			end = end.substring(0, end.length() - 2);
		}
		return end;
	}
	
	public void delete(Port port) {
		service.deletePort(port);
		englishName = null;
		coordinate = null;
		aliases = null;
		PortSearch search = getSearchBean();
		if (search.getResults() != null) {
			search.getResults().remove(port);
		}
	}
	
	public void edit(Port port) {
		setEnglishName(port.getEnglishName());
		setCoordinate(getCoordinateString(port.getGeometry()));
		setAliases(getAliasString(port));
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	
	private PortSearch getSearchBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (PortSearch) context.getApplication().evaluateExpressionGet(context, "#{PortSearch}", PortSearch.class);
	}
}
