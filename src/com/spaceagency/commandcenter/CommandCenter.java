package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.ArrayList;
import java.util.Map;

public class CommandCenter {
	private Transmitter transmitter;
	private Device device;
	private ArrayList<Device> connectedDevices = new ArrayList<>();
	ConsoleMenu menu = ConsoleMenu.getInstance();
	
	// to do: database with devices to connect to. add/remove device
	/* to do: stream online connection. Listen for connection lost.
		* return connection status. notify on success
		* retry connection
		* stay connected to multiple devices
	* */
	
	public static void main(String[] args) {
        CommandCenter commandCenter = new CommandCenter();
	}
	
	private CommandCenter() {
		transmitter = new Transmitter();
		device = new Device("Curiosity", "localhost", 1234);
		
		awaitForCommands();
	}
	
	private void awaitForCommands() {
		while(true) {
			onCommandEntered(menu.awaitUserCommand());
		}
	}
	
	private void onCommandEntered(String input) {
		switch (input) {
			case "connect": connect(); break;
			case "disconnect": disconnect(); break;
			case "help": menu.printAllCommands(); break;
			case "Invalid command": onInvalidCommand(); break;
			default:
//				System.out.println("Invalid command.");
				sendCommand(input);
		}
	}
	
	private void onInvalidCommand() {
		System.out.println("Invalid command. Please check if you spell it correctly. Mind the casing!");
	}
	
	@RemoteCommand
	public void connect() {
		ArrayList<String> remoteCommands = transmitter.connectWith(device);
		// TODO test connection lost with the rover and remove commands on disconnect
		if (remoteCommands != null && remoteCommands.size() > 0) {
			connectedDevices.add(device);
			menu.addCommands(device.getID(), remoteCommands);
		}
	}
	
	@RemoteCommand
	public void disconnect() {
		if (transmitter.disconnectWith(device)) {
			connectedDevices.remove(device);
			menu.removeCommands(device.getID());
		}
	}
	
	private void sendCommand(String command) {
		if (connectedDevices.size() > 0) {
			String response = transmitter.sendCommand(command);
			System.out.println("Response: " + response);
		}
	}
	
	
}

