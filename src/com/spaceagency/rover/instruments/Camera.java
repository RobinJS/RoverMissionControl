package com.spaceagency.rover.instruments;

public class Camera extends ElectricalInstrument {
	
	public Camera(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@Override
	public boolean isOperational() {
		return true;
	}
}
