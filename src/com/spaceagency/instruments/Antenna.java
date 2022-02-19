package com.spaceagency.instruments;

public class Antenna extends ElectricalInstrument {
	private String status = "unfolded";
	
	public Antenna(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public void unfold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "unfolded";
		}
		else {
			System.out.println("Cannot unfold antenna. Not enough power.");
		}
	}
	
	public void fold() {
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "folded";
		}
		// to do else
	}
}
