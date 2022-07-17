package com.spaceagency.rover.instruments;

import com.spaceagency.rover.interfaces.RemoteCommand;
import com.spaceagency.rover.utils.Direction;
import com.spaceagency.rover.utils.Position;

public class MovementModule extends ElectricalInstrument {
	private boolean isOperational = true;
	private Position position;
	private Direction direction;
	
	public MovementModule(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@Override
	public boolean isOperational() {
		return isOperational;
	}
	
	@RemoteCommand
	public String moveForward() {
		switch (direction) {
			case EAST -> position.x += 1;
			case WEST -> position.x -= 1;
			case NORTH -> position.y -= 1;
			case SOUTH -> position.y += 1;
		}
		
		return getLocationData();
	}
	
	@RemoteCommand
	public String moveBackward() {
		switch (direction) {
			case EAST -> position.x -= 1;
			case WEST -> position.x += 1;
			case NORTH -> position.y += 1;
			case SOUTH -> position.y -= 1;
		}
		
		return getLocationData();
	}
	
	@RemoteCommand
	public String turnLeft() {
		switch (direction) {
			case EAST -> direction = Direction.NORTH;
			case WEST -> direction = Direction.SOUTH;
			case NORTH -> direction = Direction.WEST;
			case SOUTH -> direction = Direction.EAST;
		}
		
		return getLocationData();
	}
	
	@RemoteCommand
	public String turnRight() {
		switch (direction) {
			case EAST -> direction = Direction.SOUTH;
			case WEST -> direction = Direction.NORTH;
			case NORTH -> direction = Direction.EAST;
			case SOUTH -> direction = Direction.WEST;
		}
		
		return getLocationData();
	}
	
	private String getLocationData() {
		return String.format("Pos: %s, Dir: %s", position.toString(), direction.toString());
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
