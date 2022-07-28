package com.spaceagency.commandcenter.menu;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	private	Scanner in;
	
	private Map<String, ArrayList<String>> deviceToCommands = new HashMap<>();
	
	private ConsoleMenu() { }
	
	public static ConsoleMenu getInstance() {
		if (instance == null) {
			instance = new ConsoleMenu();
			instance.init();
		}
		return instance;
	}
	
	private void init() {
		deviceToCommands.put("", new ArrayList<>(Arrays.asList("help", "connect", "disconnect")));
		in = new Scanner(System.in);
		System.out.println("Enter 'help' to see available commands. Submenu example: Rover getStatus");
	}
	
	public String awaitUserCommand() {
		return validateCommand(in.nextLine());
	}
	
	private String validateCommand(String command) {
		command.trim();
		
		String validated = "Invalid command";
		String[] commandParts = command.split(" ");
		
		if (commandParts.length > 0 && commandParts.length < 3) {
			if (commandParts.length == 1) commandParts = new String[] {"", command};
			
			ArrayList<String> possibleSubcommands = deviceToCommands.get(commandParts[0]);
			
			if (possibleSubcommands != null && possibleSubcommands.size() > 0 && possibleSubcommands.contains(commandParts[1])) {
				validated = commandParts[1];
			}
		}
		
		return validated;
	}
	
	public void addCommands(String deviceID, ArrayList<String> remoteCommands) {
		deviceToCommands.put(deviceID, remoteCommands);
	}
	
	public void removeCommands(String devideID) {
		deviceToCommands.remove(devideID);
	}
	
	public void printAllCommands() {
		deviceToCommands.forEach((k, v) -> System.out.println(k + " " + v));
	}
}
