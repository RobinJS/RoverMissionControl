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
	
	
//	@RemoteCommand
	public void moveForward() {
		// todo: check for obstacle
		switch (direction) {
			case EAST -> position.x += 1;
			case WEST -> position.x -= 1;
			case NORTH -> position.y -= 1;
			case SOUTH -> position.y += 1;
		}
	}
	
//	@RemoteCommand
	public void moveBackward() {
		// todo: check for obstacle
		switch (direction) {
			case EAST -> position.x -= 1;
			case WEST -> position.x += 1;
			case NORTH -> position.y += 1;
			case SOUTH -> position.y -= 1;
		}
	}
	
//	@RemoteCommand
	public void turnLeft() {
		switch (direction) {
			case EAST -> direction = Direction.NORTH;
			case WEST -> direction = Direction.SOUTH;
			case NORTH -> direction = Direction.WEST;
			case SOUTH -> direction = Direction.EAST;
		}
	}
	
//	@RemoteCommand
	public void turnRight() {
		switch (direction) {
			case EAST -> direction = Direction.SOUTH;
			case WEST -> direction = Direction.NORTH;
			case NORTH -> direction = Direction.EAST;
			case SOUTH -> direction = Direction.WEST;
		}
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
