package com.awesomeware.shipping.environment.model.event;

import com.awesomeware.shipping.environment.model.Port;

public class PortDataChangeEvent extends DataChangeEvent<Port> {
	private static final long serialVersionUID = 1L;

	public PortDataChangeEvent(String key, Port entity, DataChangeType type) {
		super(key, entity, type);
	}
}
