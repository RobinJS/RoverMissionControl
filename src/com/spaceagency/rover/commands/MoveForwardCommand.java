package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.MovementModule;

public class MoveForwardCommand implements Command {
	MovementModule movementModule;
	
	public MoveForwardCommand(MovementModule movementModule) {
		this.movementModule = movementModule;
	}
	
	@Override
	public String execute() {
		return movementModule.moveForward();
	}
}
