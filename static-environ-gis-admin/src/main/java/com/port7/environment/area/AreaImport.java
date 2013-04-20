package com.port7.environment.area;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.primefaces.event.FileUploadEvent;

import com.port7.environment.api.AreaServiceRemote;
import com.port7.environment.model.Area;
import com.port7.environment.model.AreaMapperLocal;
import com.port7.environment.model.AreaType;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

@ManagedBean(name = "AreaImport")
@RequestScoped
public class AreaImport {
	@EJB
	private AreaServiceRemote service;
	
	@EJB
	private AreaMapperLocal areaMapper;
	
	private String geometryField;
	private String areaNameField;
	private AreaType areaType;
	
	public String getGeometryField() {
		return geometryField;
	}

	public void setGeometryField(String geometryField) {
		this.geometryField = geometryField;
	}

	public String getAreaNameField() {
		return areaNameField;
	}

	public void setAreaNameField(String areaNameField) {
		this.areaNameField = areaNameField;
	}

	public AreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		File file = writeLocal(event);
		try {
			ShapefileDataStore store = new ShapefileDataStore(file.toURI().toURL());
			String name = store.getTypeNames()[0];
			SimpleFeatureSource source = store.getFeatureSource(name);
			SimpleFeatureCollection fsShape = source.getFeatures();
			SimpleFeatureIterator iterator = fsShape.features();
			int found = 0;
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				MultiPolygon shape = (MultiPolygon) feature.getAttribute(geometryField);
				String areaName = (String) feature.getAttribute(areaNameField);
				Area area = areaMapper.newArea(areaName, shape, areaType);
				service.updateAreaInfo(areaName, area);
				found++;
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Result", "Persisted/Updated " + found + " Areas."));
		} finally {
			file.delete();
		}
	}
	
	private File writeLocal(FileUploadEvent event) throws IOException {
		File file = File.createTempFile("import", "shp");
		try {
			IOUtils.copy(event.getFile().getInputstream(), new FileOutputStream(file));
		} catch (IOException e) {
			file.delete();
			throw e;
		}
		return file;
	}
}
