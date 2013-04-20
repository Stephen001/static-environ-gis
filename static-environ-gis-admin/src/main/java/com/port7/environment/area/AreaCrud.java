package com.port7.environment.area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.port7.environment.api.AreaServiceRemote;
import com.port7.environment.model.Area;
import com.port7.environment.model.AreaMapperLocal;
import com.port7.environment.model.AreaType;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

@ManagedBean(name = "AreaCrud")
@RequestScoped
public class AreaCrud {
	@EJB
	private AreaServiceRemote service;
	
	@EJB
	private AreaMapperLocal areaMapper;
	
	private String englishName;
	private String coordinates;
	private String aliases;
	private AreaType type;
	
	public String getEnglishName() {
		return englishName;
	}
	
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getAliases() {
		return aliases;
	}
	
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	
	public AreaType getType() {
		return type;
	}

	public void setType(AreaType type) {
		this.type = type;
	}

	public void persist() throws ParseException {
		WKTReader reader = new WKTReader();
		Area area = areaMapper.newArea(englishName, (MultiPolygon) reader.read(coordinates), type);
		service.updateAreaInfo(englishName, area);
		List<String> oldAliases = service.getAliases(area);
		List<String> newAliases = new ArrayList<>();
		if (!aliases.isEmpty()) {
			newAliases = Arrays.asList(aliases.split(", "));
		}
		List<String> removed = new ArrayList<>(oldAliases);
		removed.removeAll(newAliases);
		for (String a : removed) {
			service.removeAlias(a, area);
		}
		List<String> added = new ArrayList<>(newAliases);
		added.removeAll(oldAliases);
		for (String a : added) {
			service.addAlias(a, area);
		}
	}
	
	public String getCoordinateString(MultiPolygon polygon) {
		WKTWriter writer = new WKTWriter();
		return writer.write(polygon);
	}
	
	public String getAliasString(Area area) {
		StringBuilder builder = new StringBuilder();
		for (String alias : service.getAliases(area)) {
			builder.append(alias);
			builder.append(", ");
		}
		String end = builder.toString();
		if (end.endsWith(", ")) {
			end = end.substring(0, end.length() - 2);
		}
		return end;
	}
	
	public void delete(Area area) {
		service.deleteArea(area);
		englishName = null;
		coordinates = null;
		aliases = null;
		AreaSearch search = getSearchBean();
		if (search.getResults() != null) {
			search.getResults().remove(area);
		}
	}
	
	public void edit(Area area) {
		setEnglishName(area.getEnglishName());
		setCoordinates(getCoordinateString(area.getGeometry()));
		setAliases(getAliasString(area));
	}
	
	private AreaSearch getSearchBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (AreaSearch) context.getApplication().evaluateExpressionGet(context, "#{AreaSearch}", AreaSearch.class);
	}
}
