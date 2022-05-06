package com.example.rover;

import com.example.rover.instruments.*;
import com.example.rover.interfaces.Device;
import com.example.rover.utils.Direction;
import com.example.rover.utils.Position;
import org.springframework.web.bind.annotation.*;

@RestController
public class Rover implements Device {
	private final String id;
	private final Position position;
	private Direction direction;
	
	private final Battery battery;
	private final SolarPanel solarPanel;
	private final Camera camera;
	private final WeatherStation weatherStation;
	private final Antenna antenna;
	
	public String getId() {
		return id;
	}
	
	public Rover() {
		this("0", new Position(0, 0), Direction.EAST);
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
	}
	
	@GetMapping("/activate")
	public Message activate(@RequestParam(value = "name", defaultValue = "") String name) {
		// calibration
		// deploy antenna. no connection without antenna
		StringBuilder message = new StringBuilder();
		
		if (name.isEmpty()) {
			message.append(solarPanel.unfold());
			message.append(" ");
			message.append(antenna.unfold());
		}
		
		return new Message(message.toString());
	}
	
	public void moveForward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST: position.x += 1;
			case WEST: position.x -= 1;
			case NORTH: position.y -= 1;
			case SOUTH: position.y += 1;
		}
	}
	
	public void moveBackward() {
		// to do: check for obstacle
		switch (direction) {
			case EAST: position.x -= 1;
			case WEST: position.x += 1;
			case NORTH: position.y += 1;
			case SOUTH: position.y -= 1;
		}
	}
	
	public void turnLeft() {
		switch (direction) {
			case EAST: direction = Direction.NORTH;
			case WEST: direction = Direction.SOUTH;
			case NORTH: direction = Direction.WEST;
			case SOUTH: direction = Direction.EAST;
		}
	}
	
	public void turnRight() {
		switch (direction) {
			case EAST: direction = Direction.SOUTH;
			case WEST: direction = Direction.NORTH;
			case NORTH: direction = Direction.EAST;
			case SOUTH: direction = Direction.WEST;
		}
	}
	
	public String getStatus() {
		return battery.getStatus() + " " + solarPanel.getStatus() + " " + weatherStation.getStatus();
	}
	
	
}
