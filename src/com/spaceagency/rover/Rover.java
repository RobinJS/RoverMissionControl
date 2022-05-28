package com.spaceagency.rover;

//import com.spaceagency.commandcenter.menu.MenuItem;
import com.spaceagency.rover.instruments.*;
import com.spaceagency.rover.utils.Direction;
import com.spaceagency.rover.utils.Position;

public class Rover {
	private final String id;
	private Position position;
	private Direction direction;
	
	private Battery battery;
	private SolarPanel solarPanel;
	private Camera camera;
	private WeatherStation weatherStation;
	private Antenna antenna;
	private CommunicationModule communicationModule;
	
	public String getId() {
		return id;
	}
	
	public static void main(String[] args) {
		int port = 1234;
		Rover curiosity = new Rover("Curiosity", new Position(0, 0), Direction.EAST, port);
	}
	
	private Rover(String id, Position initialPosition, Direction direction, int port) {
		this.id = id;
		this.position = initialPosition;
		this.direction = direction;
		
		battery = new Battery();
		antenna = new Antenna(3, battery);
		solarPanel = new SolarPanel(10, battery);
		camera = new Camera(1, battery);
		weatherStation = new WeatherStation(1, battery);
		communicationModule = new CommunicationModule();
		
		battery.setCharger(solarPanel);
		
		activate();
		
		communicationModule.start(port);
	}
	
	private void activate() {
		solarPanel.unfold();
		antenna.unfold();
	}
	
//	@MenuItem
	public void moveForward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST -> position.x += 1;
			case WEST -> position.x -= 1;
			case NORTH -> position.y -= 1;
			case SOUTH -> position.y += 1;
		}
	}
	
//	@MenuItem
	public void moveBackward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST -> position.x -= 1;
			case WEST -> position.x += 1;
			case NORTH -> position.y += 1;
			case SOUTH -> position.y -= 1;
		}
	}
	
//	@MenuItem
	public void turnLeft() {
		switch (direction) {
			case EAST -> direction = Direction.NORTH;
			case WEST -> direction = Direction.SOUTH;
			case NORTH -> direction = Direction.WEST;
			case SOUTH -> direction = Direction.EAST;
		}
	}
	
//	@MenuItem
	public void turnRight() {
		switch (direction) {
			case EAST -> direction = Direction.SOUTH;
			case WEST -> direction = Direction.NORTH;
			case NORTH -> direction = Direction.EAST;
			case SOUTH -> direction = Direction.WEST;
		}
	}
	
//	@MenuItem
	public String getStatus() {
		return battery.getStatus() + " " + solarPanel.getStatus() + " " + weatherStation.getStatus();
	}
	
	
}
