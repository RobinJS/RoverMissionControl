package com.spaceagency.rover;

import com.spaceagency.common.Command;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.interfaces.RemoteCommand;
import org.hamcrest.core.AnyOf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.stream.Stream;

public class CommandExecutor {
	private Rover rover;
	private Battery battery;
	private Antenna antenna;
	private SolarPanel solarPanel;
	private Camera camera;
	private WeatherStation weatherStation;
	
	public CommandExecutor(Rover rover, Battery battery, Antenna antenna, SolarPanel solarPanel, Camera camera, WeatherStation weatherStation) {
		this.rover = rover;
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
		
		switch(commandText) {
			case "rover status": command = new RoverStatusCommand(battery, solarPanel, weatherStation); break;
			default: break;
		}
		
		return command;
	}
	
	public ArrayList<String> getRemoteCommands() {
		ArrayList<String> list = new ArrayList<>();
		
//		Stream.concat(getCommandsFor(rover));
		list.addAll(getCommandsFor(rover));
		
		return list;
	}
	
	private static <T> ArrayList<String> getCommandsFor(T obj) {
		ArrayList<String> list = new ArrayList<>();
		for (Method m : obj.getClass().getMethods()) {
			RemoteCommand mXY = m.getAnnotation(RemoteCommand.class);
			if (mXY != null) {
//				System.out.println("method: " + m.getName());
				list.add(m.getName());
			}
		}
		
		return list;
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

