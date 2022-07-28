package com.spaceagency.rover;

import com.spaceagency.rover.commands.CommandExecutor;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.interfaces.RemoteCommand;
import com.spaceagency.rover.utils.Direction;
import com.spaceagency.rover.utils.Position;

import java.io.File;

public class Rover {
	private final String id;
	
	private Battery battery;
	private SolarPanel solarPanel;
	private Camera camera;
	private WeatherStation weatherStation;
	private Antenna antenna;
	private static CommunicationModule communicationModule;
	private MovementModule movementModule;
	
	public String getId() {
		return id;
	}
	
	public static void main(String[] args) {
		int port = 1234;
		Rover rover = new Rover("Curiosity", new Position(0, 0), Direction.EAST, port);
	}
	
	private Rover(String id, Position initialPosition, Direction direction, int port) {
		this.id = id;
		System.out.println(new File("/asd").exists());
		battery = new Battery();
		antenna = new Antenna(3, battery);
		solarPanel = new SolarPanel(10, battery);
		camera = new Camera(1, battery);
		weatherStation = new WeatherStation(1, battery);
		movementModule = new MovementModule(2, battery);
		movementModule.setPosition(initialPosition);
		movementModule.setDirection(direction);
		
		battery.setCharger(solarPanel);
		
		activate();
		
		CommandExecutor commandExecutor = new CommandExecutor(this, battery, antenna, solarPanel, camera, movementModule, weatherStation);
		communicationModule = new CommunicationModule(commandExecutor);
		communicationModule.start(port);
	}
	
	private void activate() {
		solarPanel.unfold();
		antenna.unfold();
	}
	
	
	@RemoteCommand
	public String allStatus() {
		return battery.getBattery() + " " + solarPanel.getStatus() + " " + weatherStation.weather();
	}
	
	
}
