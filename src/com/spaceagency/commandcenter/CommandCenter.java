package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.rover.interfaces.RemoteCommand;

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
		
		while(true) { // todo
			onCommandEntered(menu.getNextCommand());
		}
	}
	
	private void onCommandEntered(String input) {
		switch (input) {
			case "connect": connect(); break;
			case "disconnect": disconnect(); break;
			case "help": menu.printAllCommands(); break;
			case "rover status": sendCommand(input); break;
//			case "exit": break; TODO
			default:
				System.out.println("Invalid command.");
		}
	}
	
	@RemoteCommand
	public void connect() {
		transmitter.connectWith(device);
	}
	
	@RemoteCommand
	public void disconnect() {
		transmitter.disconnectWith(device);
	}
	
	private void sendCommand(String command) {
		String response = transmitter.sendCommand(command);
		System.out.println("Response: " + response);
	}
	
	
}

