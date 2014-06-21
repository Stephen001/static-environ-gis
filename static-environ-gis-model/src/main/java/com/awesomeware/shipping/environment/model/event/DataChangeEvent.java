package com.awesomeware.shipping.environment.model.event;

import java.io.Serializable;

import com.awesomeware.shipping.environment.model.Location;

public class DataChangeEvent<T extends Location> implements Serializable {
	private static final long serialVersionUID = -7526047942301004884L;
	private final String key;
	private final T entity;
	private final DataChangeType type;
	
	public DataChangeEvent(String key, T entity, DataChangeType type) {
		this.key 	= key;
		this.entity = entity;
		this.type	= type;
	}

	public final String getKey() {
		return key;
	}

	public final T getEntity() {
		return entity;
	}

	public final DataChangeType getType() {
		return type;
	}
}
