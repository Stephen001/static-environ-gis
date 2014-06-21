package com.awesomeware.shipping.environment.port;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

import com.awesomeware.shipping.environment.api.PortServiceRemote;
import com.awesomeware.shipping.environment.model.Port;
import com.awesomeware.shipping.environment.model.PortMapperLocal;
import com.vividsolutions.jts.geom.Point;

@ManagedBean(name = "PortImport")
@SessionScoped
public class PortImport {
	@EJB
	private PortServiceRemote service;
	
	@EJB
	private PortMapperLocal portMapper;
	
	private String geometryField;
	private String portNameField;
	private UploadedFile uploaded;
	
	public String getGeometryField() {
		return geometryField;
	}

	public void setGeometryField(String geometryField) {
		this.geometryField = geometryField;
	}

	public String getPortNameField() {
		return portNameField;
	}

	public void setPortNameField(String portNameField) {
		this.portNameField = portNameField;
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
			Map<String, Port> ports = new HashMap<>();
			int count = 0;
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				Point shape = (Point) feature.getAttribute(geometryField);
				String portName = (String) feature.getAttribute(portNameField);
				Port port = portMapper.newPort(portName, shape);
				ports.put(portName, port);
				count++;
				if (count >= 200) {
					count = 0;
					service.updatePortInfo(ports);
					ports.clear();
				}
			}
			if (count > 0) {
				service.updatePortInfo(ports);
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
		zip.close();
	}
}
