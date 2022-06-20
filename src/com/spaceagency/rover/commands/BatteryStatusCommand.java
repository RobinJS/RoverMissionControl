package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.Battery;

public class BatteryStatusCommand implements Command {
	Battery battery;
	
	public BatteryStatusCommand(Battery battery) {
		this.battery = battery;
	}
	
	public String execute() {
		return battery.getStatus();
	}
}
