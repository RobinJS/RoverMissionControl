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
		switch(commandText) {
			case "allStatus": command = new RoverStatusCommand(battery, solarPanel, weatherStation); break;
			case "getWeather": command = new WeatherStationStatusCommand(weatherStation); break;
			case "getBattery": command = new BatteryStatusCommand(battery); break;
			case "takePhoto": command = new CameraTakePhotoCommand(camera); break;
			case "moveForward": command = new MoveForwardCommand(movementModule); break;
			case "moveBackward": command = new MoveBackwardCommand(movementModule); break;
			case "turnLeft": command = new TurnLeftCommand(movementModule); break;
			case "turnRight": command = new TurnRightCommand(movementModule); break;
			default: break;
		}
		
		return command;
	}
	
	public ArrayList<String> getRemoteCommands() {
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

