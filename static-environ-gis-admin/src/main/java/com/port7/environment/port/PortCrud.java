package com.port7.environment.port;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.port7.environment.api.PortServiceRemote;
import com.port7.environment.model.Port;
import com.port7.environment.model.PortMapperLocal;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@ManagedBean(name = "PortCrud")
@RequestScoped
public class PortCrud {
	private String englishName;
	private String coordinate;
	
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
	}
}
