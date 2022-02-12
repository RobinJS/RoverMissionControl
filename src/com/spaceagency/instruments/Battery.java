package com.spaceagency.instruments;

public class Battery {
	private int percentage;
	private static final int MAX_CHARGE = 100;
	
	private void charge() {
		if (percentage < MAX_CHARGE) percentage++;
	}
	
	public void consume(int requiredPower) {
		percentage -= requiredPower;
		if (percentage < 0) percentage = 0;
	}
	
	public boolean hasPower(int requiredPower) {
		return requiredPower <= percentage;
	}
}
