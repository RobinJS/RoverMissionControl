package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.commandcenter.menu.MenuItem;
import com.spaceagency.common.Command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandCenter {
	private Transmitter transmitter;
	private Device device;
	List<String> commands = new ArrayList<String>(Arrays.asList("exit", "help", "connect"));
	
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
		ConsoleMenu menu = ConsoleMenu.getInstance();
		device = new Device("Curiosity", "localhost", 1234);
		
		while(true) { // todo
			onCommandEntered(menu.getNextCommand());
		}
	}
	
	private void onCommandEntered(String input) {
		switch (input) {
			case "connect": connect(); break;
			case "disconnect": disconnect(); break;
			case "help": printAllCommands(); break;
			case "rover status": sendCommand(input); break;
			default:
				System.out.println("Invalid command.");
		}
	}
	
	@MenuItem
	public void disconnect() {
		transmitter.disconnectWith(device);
	}
	
	@MenuItem
	public void connect() {
		transmitter.connectWith(device);
	}
	
	private void sendCommand(String command) {
		String response = transmitter.sendCommand(command);
		
		System.out.println(response);
	}
	
	private static void printAllCommands() {
//		for (String c : commands) {
//			System.out.print(c + " ");
//		}
		System.out.println("all commands...");
	}
	
	
}

