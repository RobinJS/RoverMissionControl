package com.spaceagency.instruments;

import com.spaceagency.interfaces.Charger;

import java.util.Timer;
import java.util.TimerTask;

public class Battery {
	private int percentage = 100;
	private static final int MAX_CHARGE = 100;
	private Charger charger;
	private Timer timer;
	private final int timeOffsetSec = 5;
	private TimerTask task;
	
	public Battery() {
		task = new TimerTask() {
			@Override
			public void run() {
				charge();
			}
		};
		
//		startCharging();
	}
	
	private void charge() {
		if (charger != null && charger.isOperational() && percentage < MAX_CHARGE)
			percentage++;
		
		if (percentage == MAX_CHARGE) stopCharging();
		
		System.out.println(getStatus());
	}
	
	private void stopCharging() {
		timer.cancel();
		timer = null;
	}
	
	private void startCharging() {
		timer = new Timer();
		timer.schedule(task, timeOffsetSec*1000, timeOffsetSec*1000);
	}
	
	public void consume(int requiredPower) {
		percentage -= requiredPower;
		if (percentage < 0) percentage = 0;
		
//		if (timer == null) startCharging();
	}
	
	public boolean hasPower(int requiredPower) {
		return requiredPower <= percentage;
	}
	
	public String getStatus() {
		return String.format("Battery: %s%%", percentage);
	}
	
	public void setCharger(Charger charger) {
		this.charger = charger;
	}
}
