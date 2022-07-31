package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.CommandCenter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	private	Scanner in;
	private CommandCenter commandCenter;
	private Map<String, ArrayList<String>> deviceToCommands = new HashMap<>();
	
	private ConsoleMenu() { }
	
	public static ConsoleMenu getInstance(CommandCenter commandCenter) {
		if (instance == null) {
			instance = new ConsoleMenu();
			instance.init(commandCenter);
		}
		return instance;
	}
	
	private void init(CommandCenter commandCenter) {
		this.commandCenter = commandCenter;
		
		deviceToCommands.put("", new ArrayList<>(Arrays.asList("menu", "devices", "connect", "disconnect")));
		
		in = new Scanner(System.in);
		System.out.println("Enter 'menu' to see available commands. Submenu example: Rover getStatus");
	}
	
	public void awaitCommands() {
		while(true) {
			onCommandEntered(awaitUserCommand());
		}
	}
	
	public String awaitUserCommand() {
		return validateCommand(in.nextLine());
	}
	
	private void onCommandEntered(String input) {
		switch (input) {
			case "menu": printAllCommands(); break;
			case "devices": commandCenter.listDevices(); break;
			case "connect": commandCenter.connect(); break;
			case "disconnect": commandCenter.disconnect(); break;
			case "invalid": onInvalidCommand(); break;
			default:
				commandCenter.sendCommand(input);
		}
	}
	
	private String validateCommand(String command) {
		command.trim();
		
		String validated = "invalid";
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
	
	private void onInvalidCommand() {
		System.out.println("Invalid command. Please check if you spell it correctly. Mind the casing!");
	}
}
