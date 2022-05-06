package com.example.rover.instruments;

import com.example.rover.interfaces.Charger;
import com.example.rover.utils.exceptions.NotEnoughPowerException;

import java.util.Timer;
import java.util.TimerTask;

public class Battery {
	private int percentage = 100;
	private static final int MAX_CHARGE = 100;
	private Charger charger; // to do: or directly SolarPanel?
	private Timer timer;
	private final TimerTask task;
	
	public Battery() {
		task = new TimerTask() {
			@Override
			public void run() {
				tryCharging();
			}
		};
		startCharging();
	}
	
	private void tryCharging() {
		if (charger != null && charger.isOperational() && percentage < MAX_CHARGE)
			percentage++;
	}
	
	private void startCharging() {
		int timeOffsetSec = 5;
		timer = new Timer();
		timer.schedule(task, timeOffsetSec * 1000, timeOffsetSec * 1000);
	}
	
	public void consume(int requiredPower) throws NotEnoughPowerException {
		if (percentage >= requiredPower) percentage -= requiredPower;
		else throw new NotEnoughPowerException();
	}
	
	public String getStatus() {
		return String.format("Battery: %s%%", percentage);
	}
	
	public void setCharger(Charger charger) {
		this.charger = charger;
	}
}
