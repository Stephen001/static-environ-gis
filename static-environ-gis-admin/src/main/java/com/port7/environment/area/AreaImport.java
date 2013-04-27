package com.port7.environment.area;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;
import org.primefaces.model.UploadedFile;

import com.port7.environment.api.AreaServiceRemote;
import com.port7.environment.model.Area;
import com.port7.environment.model.AreaMapperLocal;
import com.port7.environment.model.AreaType;
import com.vividsolutions.jts.geom.MultiPolygon;

@ManagedBean(name = "AreaImport")
@SessionScoped
public class AreaImport {
	@EJB
	private AreaServiceRemote service;
	
	@EJB
	private AreaMapperLocal areaMapper;
	
	private String geometryField;
	private String areaNameField;
	private AreaType areaType;
	private UploadedFile uploaded;
	
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
	
	public UploadedFile getFile() {
		return uploaded;
	}

	public void setFile(UploadedFile file) throws IOException {
		this.uploaded = file;
	}
	
	private File writeLocal(UploadedFile event) throws IOException {
		File file = File.createTempFile("import", "zip");
		File upload = new File(file, event.getFileName());
		try {
			file.delete();
			file.mkdir();
			upload.createNewFile();
			IOUtils.copy(event.getInputstream(), new FileOutputStream(upload));
		} catch (IOException e) {
			upload.delete();
			file.delete();
			throw e;
		}
		return file;
	}
	
	public void save() throws IOException {
		File dir = writeLocal(uploaded);
		try {
			unzip(dir);
			File file = new File(dir, uploaded.getFileName().substring(0, uploaded.getFileName().length() - 3) + "shp");
			ShapefileDataStore store = new ShapefileDataStore(file.toURI().toURL());
			String name = store.getTypeNames()[0];
			SimpleFeatureSource source = store.getFeatureSource(name);
			SimpleFeatureCollection fsShape = source.getFeatures();
			SimpleFeatureIterator iterator = fsShape.features();
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				MultiPolygon shape = (MultiPolygon) feature.getAttribute(geometryField);
				String areaName = (String) feature.getAttribute(areaNameField);
				Area area = areaMapper.newArea(areaName, shape, areaType);
				service.updateAreaInfo(areaName, area);
			}
		} finally {
			for (Path path : dir.toPath()) {
				path.toFile().delete();
			}
		}
	}

	private void unzip(File dir) throws IOException {
		File upload = new File(dir, uploaded.getFileName());
		ZipFile zip = new ZipFile(upload);
		Enumeration<ZipArchiveEntry> entries = zip.getEntries();
		while (entries.hasMoreElements()) {
			ZipArchiveEntry entry = entries.nextElement();
			File out = new File(dir, entry.getName());
			out.createNewFile();
			IOUtils.copy(zip.getInputStream(entry), new FileOutputStream(out));
		}
	}
}
