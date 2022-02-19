package com.spaceagency.instruments;

import com.spaceagency.interfaces.Electrical;

public abstract class ElectricalInstrument implements Electrical {
	protected final int consumedPower;
	protected Battery battery;
	
	public ElectricalInstrument(int consumedPower, Battery battery) {
		this.consumedPower = consumedPower;
		this.battery = battery;
	}
	
	public abstract boolean isOperational();
}
