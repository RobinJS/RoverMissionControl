package com.spaceagency.rover.instruments;

import com.spaceagency.rover.interfaces.Charger;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.Timer;
import java.util.TimerTask;

public class Battery {
	private int percentage = 100;
	private static final int MAX_CHARGE = 100;
	private Charger charger;
	
	public Battery() {
		int timeOffsetSec = 5;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				charge();
			}
		}, timeOffsetSec * 1000, timeOffsetSec * 1000);
	}
	
	private void charge() {
		if (charger != null && charger.isOperational() && percentage < MAX_CHARGE)
			percentage++;
	}
	
	public void consume(int requiredPower) {
		percentage -= requiredPower;
		if (percentage < 0) percentage = 0;
	}
	
	public boolean hasPower(int requiredPower) {
		return requiredPower <= percentage;
	}
	
	@RemoteCommand
	public String getBattery() {
		return String.format("Battery: %s%%.", percentage);
	}
	
	public void setCharger(Charger charger) {
		this.charger = charger;
	}
}
