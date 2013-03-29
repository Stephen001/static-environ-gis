package com.port7.environment.model.event;

import com.port7.environment.model.Port;

public class PortDataChangeEvent extends DataChangeEvent<Port> {
	private static final long serialVersionUID = 1L;

	public PortDataChangeEvent(String key, Port entity, DataChangeType type) {
		super(key, entity, type);
	}
}
