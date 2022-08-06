package com.spaceagency.rover.instruments;

import com.spaceagency.rover.interfaces.Charger;

public class SolarPanel extends ElectricalInstrument implements Charger {
	private boolean unfolded = false;
	private boolean exposedToLight = true;
	
	public SolarPanel(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		String statusWord = unfolded ? "unfolded" : "folded";
		String producingWord = exposedToLight ? "yes" : "no";
		return String.format("Solar panel is %1$s. Exposed to light: %2$s", statusWord, producingWord);
	}
	
	public void unfold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = true;
			System.out.println("Solar Panel unfolded.");
		}
		else {
			System.out.println("Cannot unfold solar panel. Not enough power.");
		}
	}
	
	public void fold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = false;
		}
		else {
			System.out.println("Cannot fold solar panel. Not enough power.");
		}
	}
	
	@Override
	public boolean isOperational() {
		return unfolded && exposedToLight;
	}
}
