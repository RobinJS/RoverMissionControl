package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.Battery;
import com.spaceagency.rover.instruments.SolarPanel;
import com.spaceagency.rover.instruments.WeatherStation;

class RoverStatusCommand implements Command {
	Battery battery;
	SolarPanel solarPanel;
	WeatherStation weatherStation;
	
	public RoverStatusCommand(Battery battery, SolarPanel solarPanel, WeatherStation weatherStation) {
		this.battery = battery;
		this.solarPanel = solarPanel;
		this.weatherStation = weatherStation;
	}
	
	public String execute() { // TODO: response data
		return battery.getStatus() + " " + solarPanel.getStatus() + " " + weatherStation.getStatus();
	}
}
