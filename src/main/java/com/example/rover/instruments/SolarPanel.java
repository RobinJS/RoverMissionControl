package com.example.rover.instruments;

import com.example.rover.interfaces.Charger;
import com.example.rover.utils.exceptions.NotEnoughPowerException;

public class SolarPanel extends ElectricalInstrument implements Charger {
	private boolean unfolded = false;
	private boolean exposedToLight = true; // to do
	
	public SolarPanel(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		String statusWord = unfolded ? "unfolded" : "folded";
		String producingWord = exposedToLight ? "yes" : "no";
		return "Solar panel is " + statusWord + ". Exposed to light: " + producingWord; // to do json?
	}
	
	public String unfold() {
		String message = "";
		
		if (unfolded) {
			message = "Solar Panel already unfolded.";
		}
		else {
			try	{
				battery.consume(consumedPower);
				unfolded = true;
				message = "Solar Panel unfolded.";
			} catch (NotEnoughPowerException e) {
				message = "Cannot unfold Solar Panel. " + e.getMessage();
			}
		}
		
		return message;
	}
	
	public String fold() {
		String message = "";
		
		if (unfolded) {
			message = "Solar Panel already folded.";
		}
		else {
			try	{
				battery.consume(consumedPower);
				unfolded = false;
				message = "Solar Panel folded.";
			} catch (NotEnoughPowerException e) {
				message = "Cannot fold Solar Panel. " + e.getMessage();
			}
		}
		
		return message;
	}
	
	@Override
	public boolean isOperational() {
		return unfolded && exposedToLight;
	}
}
