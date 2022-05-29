package com.spaceagency.rover;

import com.spaceagency.common.Command;

public class CommandExecutor {
	Rover rover;
	
	public CommandExecutor(Rover rover) {
		this.rover = rover;
	}
	
	public String runCommand(Command command) {
		System.out.println("recieved command " + command.getExecutorName() + " " + command.getCommandName());
		return "";
	}
	
	public boolean execute(String command) {
		switch(command) {
			case "rover status": new RoverStatus(rover).execute(); break;
			default: break;
		}
		
		return false;
	}
	
	public class RoverStatus {
		Rover rover;
		public RoverStatus(Rover rover) {
			this.rover = rover;
		}
		
		public String execute() { // to do response data
			return rover.getStatus();
		}
	}
}

