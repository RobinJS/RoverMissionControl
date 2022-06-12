package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.ArrayList;
import java.util.Map;

public class CommandCenter {
	private Transmitter transmitter;
	private Device device;
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
		
//		connect(); // to remove
		
		while(true) { // TODO
			onCommandEntered(menu.awaitUserCommand());
		}
	}
	
	private void onCommandEntered(String input) {
		switch (input) {
			case "connect": connect(); break;
			case "disconnect": disconnect(); break;
			case "help": menu.printAllCommands(); break;
//			case "rover status": sendCommand(input); break;
//			case "exit": break; TODO
			default:
//				System.out.println("Invalid command.");
				sendCommand(input);
		}
	}
	
	@RemoteCommand
	public void connect() {
		Map<String, ArrayList<String>> remoteCommands = transmitter.connectWith(device);
		// TODO test connection lost with the rover
		if (remoteCommands != null && remoteCommands.size() > 0) {
			menu.addCommands(remoteCommands);
		}
	}
	
	@RemoteCommand
	public void disconnect() {
		// TODO disconnect if already connected. Remove commands
		transmitter.disconnectWith(device);
	}
	
	private void sendCommand(String command) {
		// TODO if connected to a device
		String response = transmitter.sendCommand(command);
		System.out.println("Response: " + response);
	}
	
	
}

