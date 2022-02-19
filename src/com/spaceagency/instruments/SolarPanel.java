package com.spaceagency.instruments;

import com.spaceagency.interfaces.Charger;

public class SolarPanel extends ElectricalInstrument implements Charger {
	private boolean unfolded = false;
	private boolean exposedToLight = true; // to do
	
	public SolarPanel(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		String statusWord = unfolded ? "unfolded" : "folded";
		String producingWord = exposedToLight ? "yes" : "no";
		return "Solar panel is " + statusWord + ". Exposed to light: " + exposedToLight; // to do json?
	}
	
	public void unfold() { // bool
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = true;
			System.out.println("Solar Panel unfolded.");
		}
		// to do else... not enough power
	}
	
	public void fold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = false;
		}
		// to do else... not enough power
	}
	
	@Override
	public boolean isOperational() {
		return unfolded && exposedToLight;
	}
}
