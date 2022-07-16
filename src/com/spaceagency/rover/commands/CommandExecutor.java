package com.spaceagency.rover.commands;

import com.spaceagency.rover.Rover;
import com.spaceagency.rover.commands.Command;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.lang.reflect.Method;
import java.util.*;

public class CommandExecutor {
	private Rover rover;
	private Battery battery;
	private Antenna antenna;
	private SolarPanel solarPanel;
	private Camera camera;
	private MovementModule movementModule;
	private WeatherStation weatherStation;
	private Map<String, ArrayList<String>> availableCommands = new HashMap<>();
	
	public CommandExecutor(Rover rover, Battery battery, Antenna antenna, SolarPanel solarPanel, Camera camera, MovementModule movementModule, WeatherStation weatherStation) {
		this.rover = rover;
		this.battery = battery;
		this.antenna = antenna;
		this.solarPanel = solarPanel;
		this.camera = camera;
		this.movementModule = movementModule;
		this.weatherStation = weatherStation;
	}
	
	public String runCommand(String requestedCommand) {
		Command command = evaluateCommand(requestedCommand);
		if (command != null) return command.execute();
		else return "Invalid command: " + requestedCommand;
	}
	
	private Command evaluateCommand(String commandText) {
		Command command = null;
		// TODO: make it so it won't be needed to hardcode all these commands in the swithc
		switch(commandText) {
			case "Rover getStatus": command = new RoverStatusCommand(battery, solarPanel, weatherStation); break;
			case "WeatherStation getInfo": command = new WeatherStationStatusCommand(weatherStation); break;
			case "Battery getStatus": command = new BatteryStatusCommand(battery); break;
			case "Camera takePhoto": command = new CameraTakePhotoCommand(camera); break;
			default: break;
		}
		
		return command;
	}
	
	public Map<String, ArrayList<String>> getRemoteCommands() {
		// TODO evaluate on init
		Map<String, ArrayList<String>> commandsMap = new HashMap<>();
		
		// TODO: or use objects
		ArrayList<String> roverCommands = getCommandsFor(rover);
		if (roverCommands.size() > 0) commandsMap.put(rover.getClass().getSimpleName(), roverCommands);
		
		ArrayList<String> batteryCommands = getCommandsFor(battery);
		if (batteryCommands.size() > 0) commandsMap.put(battery.getClass().getSimpleName(), batteryCommands);

		ArrayList<String> cameraCommands = getCommandsFor(camera);
		if (cameraCommands.size() > 0) commandsMap.put(camera.getClass().getSimpleName(), cameraCommands);

		ArrayList<String> movementCommands = getCommandsFor(movementModule);
		if (movementCommands.size() > 0) commandsMap.put(movementModule.getClass().getSimpleName(), movementCommands);

		ArrayList<String> weatherStationCommands = getCommandsFor(weatherStation);
		if (weatherStationCommands.size() > 0) commandsMap.put(weatherStation.getClass().getSimpleName(), weatherStationCommands);
		
		return commandsMap;
	}
	
	private static <T> ArrayList<String> getCommandsFor(T obj) {
		ArrayList<String> list = new ArrayList<>();
		
		for (Method m : obj.getClass().getMethods()) {
			RemoteCommand mXY = m.getAnnotation(RemoteCommand.class);
			if (mXY != null) {
				list.add(m.getName());
			}
		}
		
		return list;
	}
	
}

