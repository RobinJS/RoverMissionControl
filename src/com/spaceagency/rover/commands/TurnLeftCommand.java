package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.MovementModule;

public class TurnLeftCommand implements Command {
	MovementModule movementModule;
	
	public TurnLeftCommand(MovementModule movementModule) {
		this.movementModule = movementModule;
	}
	
	@Override
	public String execute() {
		return movementModule.turnLeft();
	}
}
