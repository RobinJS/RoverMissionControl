package com.spaceagency.rover;

import com.spaceagency.common.Command;

public class CommandExecutor {
	Antenna antenna;
	SolarPanel solarPanel;
	Battery battery;
	
	public CommandExecutor(Antenna antenna, SolarPanel solarPanel, Battery battery) {
		this.antenna = antenna;
		this.solarPanel = solarPanel;
		this.battery = battery;
	}
	
	public String runCommand(String commandText) {
		Command command = evaluateCommand(commandText);
		System.out.println("recieved command " + command.getExecutorName() + " " + command.getCommandName());
		return "";
	}
	
	private Command evaluateCommand(String commandText) {
		Command command;
		
		
		return command;
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

