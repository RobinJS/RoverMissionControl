package com.spaceagency.rover.instruments;

public class Antenna extends ElectricalInstrument {
	private boolean unfolded = false;
	
	public Antenna(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public void unfold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = true;
			System.out.println("Antenna unfolded.");
		}
		else {
			System.out.println("Cannot unfold antenna. Not enough power.");
		}
	}
	
	public void fold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = false;
		}
		else {
			System.out.println("Cannot fold antenna. Not enough power.");
		}
	}
	
	@Override
	public boolean isOperational() {
		return unfolded;
	}
}
