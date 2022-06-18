package com.spaceagency.commandcenter.menu;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	private	Scanner in;
	
	private Map<String, ArrayList<String>> commands = new HashMap<>();
	
	private ConsoleMenu() { }
	
	public static ConsoleMenu getInstance() {
		if (instance == null) {
			instance = new ConsoleMenu();
			instance.init();
		}
		return instance;
	}
	
	private void init() {
		commands.put("", new ArrayList<>(Arrays.asList("help", "connect", "disconnect")));
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
			
			ArrayList<String> possibleSubcommands = commands.get(commandParts[0]);
			
			if (possibleSubcommands.size() > 0 && possibleSubcommands.contains(commandParts[1])) {
				validated = command;
			}
		}
		
		return validated;
	}
	
	public void addCommands(String deviceID, Map<String, ArrayList<String>> commandsMap) {
		commands = Stream.of(commands, commandsMap)
				.flatMap(map -> map.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	public void printAllCommands() {
		commands.forEach((k, v) -> System.out.println(k + " " + v));
	}
}