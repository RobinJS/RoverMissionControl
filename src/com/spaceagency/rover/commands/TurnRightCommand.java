package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.MovementModule;

public class TurnRightCommand implements Command {
	MovementModule movementModule;
	
	public TurnRightCommand(MovementModule movementModule) {
		this.movementModule = movementModule;
	}
	
	@Override
	public String execute() {
		return movementModule.turnRight();
	}
}
