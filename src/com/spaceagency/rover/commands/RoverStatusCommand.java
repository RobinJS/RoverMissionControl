package com.spaceagency.rover.commands;

import com.spaceagency.rover.Rover;

class RoverStatusCommand implements Command {
	Rover rover;
	
	public RoverStatusCommand(Rover rover) {
		this.rover = rover;
	}
	
	public String execute() {
		return rover.allStatus();
	}
}
