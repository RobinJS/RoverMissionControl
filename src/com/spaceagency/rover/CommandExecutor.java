package com.spaceagency.rover;

import com.spaceagency.common.Command;
import com.spaceagency.rover.instruments.*;

public class CommandExecutor {
	private Battery battery;
	private Antenna antenna;
	private SolarPanel solarPanel;
	private Camera camera;
	private WeatherStation weatherStation;
	
	public CommandExecutor(Battery battery, Antenna antenna, SolarPanel solarPanel, Camera camera, WeatherStation weatherStation) {
		this.battery = battery;
		this.antenna = antenna;
		this.solarPanel = solarPanel;
		this.camera = camera;
		this.weatherStation = weatherStation;
	}
	
	public String runCommand(String commandText) {
		Command command = evaluateCommand(commandText);
		if (command != null) return command.execute();
		else return "error";
	}
	
	private Command evaluateCommand(String commandText) {
		Command command = null;
		
		System.out.println("Check command " + commandText);
		
		switch(commandText) {
			case "rover status": command = new RoverStatusCommand(battery, solarPanel, weatherStation); break;
			default: break;
		}
		
		return command;
	}
	
	public class RoverStatusCommand implements Command {
		Battery battery;
		SolarPanel solarPanel;
		WeatherStation weatherStation;
		
		public RoverStatusCommand(Battery battery, SolarPanel solarPanel, WeatherStation weatherStation) {
			this.battery = battery;
			this.solarPanel = solarPanel;
			this.weatherStation = weatherStation;
		}
		
		public String execute() { // to do response data
			return battery.getStatus() + " " + solarPanel.getStatus() + " " + weatherStation.getStatus();
		}
	}
}

