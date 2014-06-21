package com.awesomeware.shipping.environment.model.event;

import com.awesomeware.shipping.environment.model.Area;

public final class AreaDataChangeEvent extends DataChangeEvent<Area> {
	private static final long serialVersionUID = 8322527025237801052L;

	public AreaDataChangeEvent(String key, Area entity, DataChangeType type) {
		super(key, entity, type);
	}
}
