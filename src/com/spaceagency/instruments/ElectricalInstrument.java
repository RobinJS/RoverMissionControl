package com.spaceagency.instruments;

import com.spaceagency.instruments.Battery;

public class ElectricalInstrument {
	protected final int consumedPower;
	protected Battery battery;
	
	public ElectricalInstrument(int consumedPower, Battery battery) {
		this.consumedPower = consumedPower;
		this.battery = battery;
	}
	
}
