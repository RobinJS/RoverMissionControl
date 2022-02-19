package com.spaceagency.instruments;

import com.spaceagency.commandcenter.menu.MenuItem;
import com.spaceagency.interfaces.Charger;

public class SolarPanel extends ElectricalInstrument implements Charger {
	private boolean unfolded = false;
	private boolean exposedToLight = true; // to do
	
	public SolarPanel(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@MenuItem
	public String getStatus() {
		String statusWord = unfolded ? "unfolded" : "folded";
		String producingWord = exposedToLight ? "yes" : "no";
		return "Solar panel is " + statusWord + ". Exposed to light: " + producingWord; // to do json?
	}
	
	@MenuItem
	public void unfold() { // bool
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = true;
			System.out.println("Solar Panel unfolded.");
		}
		else {
			System.out.println("Cannot unfold solar panel. Not enough power.");
		}
	}
	
	@MenuItem
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
