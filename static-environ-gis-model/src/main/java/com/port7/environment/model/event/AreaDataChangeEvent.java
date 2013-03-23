package com.port7.environment.model.event;

import com.port7.environment.model.Area;

public final class AreaDataChangeEvent extends DataChangeEvent<Area> {
	private static final long serialVersionUID = 8322527025237801052L;

	public AreaDataChangeEvent(String key, Area entity, DataChangeType type) {
		super(key, entity, type);
	}
}
