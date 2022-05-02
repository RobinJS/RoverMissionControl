package com.example.rover.instruments;

public class Antenna extends ElectricalInstrument {
	private boolean unfolded = false;
	
	public Antenna(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String unfold() {
		String message = "";
		
		if (unfolded) {
			message = "Antenna already unfolded.";
		}
		else if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			unfolded = true;
			message = "Antenna unfolded.";
		}
		else {
			message = "Cannot unfold antenna. Not enough power.";
		}
		
		return message;
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
