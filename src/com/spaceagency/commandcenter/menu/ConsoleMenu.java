package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.CommandCenter;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	private	Scanner in;
	private CommandCenter commandCenter;
	private Map<String, List<MenuOption>> deviceToMenuOptions = new HashMap<>();
	private String currentDeviceMenu;
	
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
		in = new Scanner(System.in);
		
		deviceToMenuOptions.put("commandCenter", Arrays.asList(
				new MenuOption("menu"),
				new MenuOption("devices"),
				new MenuOption("add"),
				new MenuOption("remove"),
				new MenuOption("connect")
			));
		
		currentDeviceMenu = "commandCenter";
		
		System.out.println("Enter 'menu' to see available commands. Submenu example: Rover getStatus");
	}
	
	public void awaitCommands() {
		while(true) {
//			onCommandEntered(evaluateCommand(in.nextLine()));
			runCommand(validateInput(in.nextLine()));
		}
	}
	
//	public String awaitUserCommand() {
//		return validateCommand(in.nextLine());
//	}
	
	private void runCommand(MenuOption input) {
		switch (input.command) {
			case "menu": printAvailableCommands(); break;
			case "devices": commandCenter.listDevices(); break;
			case "add": commandCenter.onAddCommand(input); break;
			case "remove": commandCenter.onRemoveCommand(input); break;
			case "connect": commandCenter.connect(input); break;
//			case "disconnect": commandCenter.disconnect(); break;
			case "invalid": onInvalidCommand(); break;
			default:
				commandCenter.sendCommand("input");
		}
		
		// add menu and disconnect
	}
	
	private MenuOption validateInput(String input) {
		input = input.trim();
		
		List<String> inputParts = Arrays.asList(input.split(" "));
		String command = inputParts.get(0);
		MenuOption existingOption = getMenuOption(command);
		
		return existingOption != null ? existingOption : new MenuOption("invalid");
	}
	
	private MenuOption getMenuOption(String command) {
		List<MenuOption> availableOptions = deviceToMenuOptions.get(currentDeviceMenu);
		return availableOptions.stream().filter(o -> o.command.equals(command)).findAny().orElse(null);
	}
	
	public void onDeviceConnected(String deviceID, List<String> remoteCommands) {
		// TODO create MenuOptions from remoteCommands
		deviceToMenuOptions.put(deviceID, getCommandOptions(remoteCommands));
		currentDeviceMenu = deviceID;
		System.out.println("More commands available.");
	}
	
	private List<MenuOption> getCommandOptions(List<String> remoteCommands) {
//		Arrays.asList(
//				new MenuOption("menu"),
//				new MenuOption("devices"),
//				new MenuOption("add"),
//				new MenuOption("remove"),
//				new MenuOption("connect")
//			)
			
		return remoteCommands.stream().map(MenuOption::new).collect(Collectors.toList());
	}
	
	public void onDeviceDisconnected(String devideID) {
		deviceToMenuOptions.remove(devideID);
		currentDeviceMenu = "commandCenter";
	}
	
	public void printAvailableCommands() {
		System.out.println(deviceToMenuOptions.get(currentDeviceMenu));
	}
	
	private void onInvalidCommand() {
		System.out.println("Invalid command. Please check if you spell it correctly. Mind the casing!");
	}
}
