package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.common.MenuOption;

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
				new MenuOption("add", "<deviceName>"),
				new MenuOption("remove", "<deviceName>"),
				new MenuOption("connect", "<deviceName>")
			));
		
		currentDeviceMenu = "commandCenter";
		
		System.out.println("Enter 'menu' to see available commands. Submenu example: Rover getStatus");
	}
	
	public void awaitCommands() {
		while(true) {
			runCommand(validateInput(in.nextLine()));
		}
	}
	
	private void runCommand(MenuOption input) {
		if (input != null) {
			switch (input.command) {
				case "menu": printAvailableCommands(); break;
				case "devices": commandCenter.listDevices(); break;
				case "add": commandCenter.onAddCommand(input); break;
				case "remove": commandCenter.onRemoveCommand(input); break;
				case "connect": commandCenter.connect(input); break;
				case "disconnect": commandCenter.disconnect(); break;
				default:
					commandCenter.sendCommand(input);
			}
		}
	}
	
	private MenuOption validateInput(String input) {
		input = input.trim();
		
		List<String> inputParts = Arrays.asList(input.split(" "));
		
		String command = inputParts.get(0);
		List<String> params = inputParts.size() > 1 ? inputParts.subList(1, inputParts.size()) : null;
		
		MenuOption existingOption = getMenuOption(command);
		MenuOption newMenuCommand = null;
		
		if (existingOption == null) {
			System.out.println("Invalid command. Please check if you spell it correctly. Mind the casing.");
		}
		else if (existingOption.requiredParams != null && params == null) {
			System.out.println("Missing required params for " + command + ": " + existingOption.requiredParams);
		}
		else newMenuCommand = new MenuOption(existingOption.command, existingOption.requiredParams, params);
		
		return newMenuCommand;
	}
	
	private MenuOption getMenuOption(String command) {
		List<MenuOption> availableOptions = deviceToMenuOptions.get(currentDeviceMenu);
		return availableOptions.stream().filter(o -> o.command.equals(command)).findAny().orElse(null);
	}
	
	public void onDeviceConnected(String deviceID, List<String> remoteCommands) {
		// TODO create MenuOptions from remoteCommands
		List<MenuOption> modifiedDeviceOptions = getCommandOptions(remoteCommands);
		modifyDeviceOptions(modifiedDeviceOptions);
		
		deviceToMenuOptions.put(deviceID, modifiedDeviceOptions);
		currentDeviceMenu = deviceID;
		System.out.println("More commands available.");
	}
	
	private void modifyDeviceOptions(List<MenuOption> deviceOptions) {
		deviceOptions.add(new MenuOption("menu"));
		deviceOptions.add(new MenuOption("disconnect"));
	}
	
	private List<MenuOption> getCommandOptions(List<String> remoteCommands) {
		return remoteCommands.stream().map(MenuOption::new).collect(Collectors.toList());
	}
	
	public void onDeviceDisconnected(String devideID) {
		deviceToMenuOptions.remove(devideID);
		currentDeviceMenu = "commandCenter";
	}
	
	public void printAvailableCommands() {
		String commands = deviceToMenuOptions.get(currentDeviceMenu)
				.stream().map(this::mapCommandInfo)
				.collect(Collectors.joining(", "));
		
		System.out.println(commands);
	}
	
	private String mapCommandInfo(MenuOption mo) {
		return mo.requiredParams == null ? mo.command : mo.command + " " + mo.requiredParams;
	}
}
