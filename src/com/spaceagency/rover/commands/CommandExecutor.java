package com.spaceagency.rover.commands;

import com.spaceagency.rover.Rover;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.lang.reflect.Method;
import java.util.*;

public class CommandExecutor {
	private final Rover rover;
	private final Battery battery;
	private final Antenna antenna;
	private final SolarPanel solarPanel;
	private final Camera camera;
	private final MovementModule movementModule;
	private final WeatherStation weatherStation;
	private Map<String, Command> availableCommands = new HashMap<>();
	
	public CommandExecutor(Rover rover, Battery battery, Antenna antenna, SolarPanel solarPanel, Camera camera, MovementModule movementModule, WeatherStation weatherStation) {
		this.rover = rover;
		this.battery = battery;
		this.antenna = antenna;
		this.solarPanel = solarPanel;
		this.camera = camera;
		this.movementModule = movementModule;
		this.weatherStation = weatherStation;
		
		initCommands();
	}
	
	private void initCommands() {
		availableCommands.put("allStatus", new RoverStatusCommand(rover));
		availableCommands.put("weather", new WeatherStationStatusCommand(weatherStation));
		availableCommands.put("getBattery", new BatteryStatusCommand(battery));
		availableCommands.put("takePhoto", new CameraTakePhotoCommand(camera));
		availableCommands.put("moveForward", new MoveForwardCommand(movementModule));
		availableCommands.put("moveBackward", new MoveBackwardCommand(movementModule));
		availableCommands.put("turnLeft", new TurnLeftCommand(movementModule));
		availableCommands.put("turnRight", new TurnRightCommand(movementModule));
	}
	
	public String runCommand(String requestedCommand) {
		if (availableCommands.containsKey(requestedCommand)) return availableCommands.get(requestedCommand).execute();
		else return "Invalid command: " + requestedCommand;
	}
	
	public ArrayList<String> getRemoteCommands() {
		// TODO: compare this logic with the one in initCommands. Maby use this one only
		ArrayList<String> commandsMap = new ArrayList<>();
		
		commandsMap.addAll(getCommandsFor(rover));
		commandsMap.addAll(getCommandsFor(battery));
		commandsMap.addAll(getCommandsFor(camera));
		commandsMap.addAll(getCommandsFor(movementModule));
		commandsMap.addAll(getCommandsFor(weatherStation));
		
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

