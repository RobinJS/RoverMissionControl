package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.MovementModule;

public class MoveBackwardCommand implements Command {
	MovementModule movementModule;
	
	public MoveBackwardCommand(MovementModule movementModule) {
		this.movementModule = movementModule;
	}
	
	@Override
	public String execute() {
		return movementModule.moveBackward();
	}
}
