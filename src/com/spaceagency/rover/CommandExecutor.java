package com.spaceagency.rover;

import com.spaceagency.common.Command;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.interfaces.RemoteCommand;
import org.hamcrest.core.AnyOf;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class CommandExecutor {
	private Rover rover;
	private Battery battery;
	private Antenna antenna;
	private SolarPanel solarPanel;
	private Camera camera;
	private MovementModule movementModule;
	private WeatherStation weatherStation;
	
	public CommandExecutor(Rover rover, Battery battery, Antenna antenna, SolarPanel solarPanel, Camera camera, MovementModule movementModule, WeatherStation weatherStation) {
		this.rover = rover;
		this.battery = battery;
		this.antenna = antenna;
		this.solarPanel = solarPanel;
		this.camera = camera;
		this.movementModule = movementModule;
		this.weatherStation = weatherStation;
	}
	
	public String runCommand(String commandText) {
		Command command = evaluateCommand(commandText);
		if (command != null) return command.execute();
		else return "Invalid command: " + commandText;
	}
	
	private Command evaluateCommand(String commandText) {
		Command command = null;
		
		switch(commandText) {
			case "Rover getStatus": command = new RoverStatusCommand(battery, solarPanel, weatherStation); break;
			default: break;
		}
		
		return command;
	}
	
	public Map<String, ArrayList<String>> getRemoteCommands() {
		// TODO evaluate on init
		Map<String, ArrayList<String>> commandsMap = new HashMap<>();
		
		// TODO: or use objects
		Map.Entry<String,ArrayList<String>> roverCommands = getCommandsFor(rover).entrySet().iterator().next();
		commandsMap.put(roverCommands.getKey(), roverCommands.getValue());
		
		Map.Entry<String,ArrayList<String>> batteryCommands = getCommandsFor(battery).entrySet().iterator().next();
		commandsMap.put(batteryCommands.getKey(), batteryCommands.getValue());
		
		Map.Entry<String,ArrayList<String>> cameraCommands = getCommandsFor(camera).entrySet().iterator().next();
		commandsMap.put(cameraCommands.getKey(), cameraCommands.getValue());
		
		Map.Entry<String,ArrayList<String>> movementCommands = getCommandsFor(movementModule).entrySet().iterator().next();
		commandsMap.put(movementCommands.getKey(), movementCommands.getValue());
		
		Map.Entry<String,ArrayList<String>> weatherStationCommands = getCommandsFor(weatherStation).entrySet().iterator().next();
		commandsMap.put(weatherStationCommands.getKey(), weatherStationCommands.getValue());
		
		
		return commandsMap;
	}
	
	private static <T> Map<String, ArrayList<String>> getCommandsFor(T obj) {
		Map<String, ArrayList<String>> commandsMap = new HashMap<>();
		ArrayList<String> list = new ArrayList<>();
		
		for (Method m : obj.getClass().getMethods()) {
			RemoteCommand mXY = m.getAnnotation(RemoteCommand.class);
			if (mXY != null) {
				list.add(m.getName());
			}
		}
		
		commandsMap.put(obj.getClass().getSimpleName(), list);
		
		return commandsMap;
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
		
		public String execute() { // TODO: response data
			return battery.getStatus() + " " + solarPanel.getStatus() + " " + weatherStation.getStatus();
		}
	}
}
