package com.spaceagency.commandcenter;

import java.io.Serializable;

public class Command implements Serializable {
	private final String executorName;
	private final String commandName;
	
	public Command(String executorName, String commandName) {
		this.executorName = executorName;
		this.commandName = commandName;
	}
	
	public String getExecutorName() {
		return executorName;
	}
	
	public String getCommandName() {
		return commandName;
	}
}
