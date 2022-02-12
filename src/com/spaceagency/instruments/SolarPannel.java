package com.spaceagency.instruments;

public class SolarPannel extends ElectricalInstrument {
	private String status = "unfolded";
	
	public SolarPannel(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		return status;
	}
	
	public void unfold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "unfolded";
		}
		// to do else
	}
	
	public void fold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "folded";
		}
		// to do else
	}
}
