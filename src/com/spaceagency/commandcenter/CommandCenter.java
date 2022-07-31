package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.devices.Device;
import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommandCenter {
	private Transmitter transmitter;
	private ArrayList<Device> devices = new ArrayList<>();
	private final ConsoleMenu menu;
	
	private Device device;
	
	// to do: database with users
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
		
		menu = ConsoleMenu.getInstance(this);
		menu.awaitCommands();
		
		/* Test */
		devices.add(new Device("Curiosity", "localhost", 1234));
	}
	
	public void connect() {
		ArrayList<String> remoteCommands = transmitter.connectWith(device);
		// TODO test connection lost with the rover and remove commands on disconnect
		if (remoteCommands != null && remoteCommands.size() > 0) {
			devices.add(device);
			menu.addCommands(device.getID(), remoteCommands);
		}
	}
	
	public void disconnect() {
		if (devices.size() == 0) {
			System.out.println("There are no connected devices.");
		}
		else if (transmitter.disconnectWith(device)) {
			devices.remove(device);
			menu.removeCommands(device.getID());
		}
	}
	
	public void listDevices() {
		if (devices.size() > 0) {
			String allDevices = devices.stream()
					.map(x -> x.getID())
					.collect(Collectors.joining(","))
					.toString();
			System.out.println(allDevices);
		}
		else {
			System.out.println("There are no connected devices.");
		}
	}
	
	public void sendCommand(String command) {
		if (devices.size() > 0) {
			String response = transmitter.sendCommand(command);
			System.out.println("Response: " + response);
		}
	}
	
	
}

