package com.example.rover.instruments;

import com.example.rover.utils.exceptions.NotEnoughPowerException;

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
		else {
			try	{
				battery.consume(consumedPower);
				unfolded = true;
				message = "Antenna unfolded.";
			} catch (NotEnoughPowerException e) {
				message = "Cannot unfold antenna. " + e.getMessage();
			}
		}
		
		return message;
	}
	
	public void fold() {
		String message = "";
		
		if (!unfolded) {
			message = "Antenna already folded.";
		}
		else {
			try {
				battery.consume(consumedPower);
				unfolded = false;
				message = "Antenna folded.";
			}
			catch (NotEnoughPowerException e) {
				message = "Cannot fold antenna. " + e.getMessage();
			}
		}
	}
	
	@Override
	public boolean isOperational() {
		return unfolded;
	}
}
