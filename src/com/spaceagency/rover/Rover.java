package com.spaceagency.rover;

import com.spaceagency.instruments.*;
import com.spaceagency.interfaces.Device;
import com.spaceagency.utils.Direction;
import com.spaceagency.utils.Position;

public class Rover implements Device {
	private final String id;
	private Position position;
	private Direction direction;
	
	private Battery battery;
	private SolarPanel solarPanel;
	private Camera camera;
	private WeatherStation weatherStation;
	private Antenna antenna;
	
	public String getId() {
		return id;
	}
	
	public Rover(String id, Position initialPosition, Direction direction) {
		this.id = id;
		this.position = initialPosition;
		this.direction = direction;
		
		battery = new Battery();
		antenna = new Antenna(3, battery);
		solarPanel = new SolarPanel(10, battery);
		camera = new Camera(1, battery);
		weatherStation = new WeatherStation(1, battery);
		
		battery.setCharger(solarPanel);
		
		activate();
	}
	
	public void activate() {
		// calibration
		// deploy antenna. no connection without antenna
		
		antenna.unfold();
		solarPanel.unfold();
		
		// send signal for successful activation
	}
	
	public void moveForward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST -> position.x += 1;
			case WEST -> position.x -= 1;
			case NORTH -> position.y -= 1;
			case SOUTH -> position.y += 1;
		}
	}
	
	public void moveBackward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST -> position.x -= 1;
			case WEST -> position.x += 1;
			case NORTH -> position.y += 1;
			case SOUTH -> position.y -= 1;
		}
	}
	
	public void turnLeft() {
		switch (direction) {
			case EAST -> direction = Direction.NORTH;
			case WEST -> direction = Direction.SOUTH;
			case NORTH -> direction = Direction.WEST;
			case SOUTH -> direction = Direction.EAST;
		}
	}
	
	public void turnRight() {
		switch (direction) {
			case EAST -> direction = Direction.SOUTH;
			case WEST -> direction = Direction.NORTH;
			case NORTH -> direction = Direction.EAST;
			case SOUTH -> direction = Direction.WEST;
		}
	}
	
	public String getStatus() {
		return battery.getStatus();
	}
	
	
}
