package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.devices.Device;
import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.commandcenter.menu.MenuOption;
import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommandCenter {
	private Transmitter transmitter;
	private ArrayList<Device> devices = new ArrayList<>();
	private final ConsoleMenu menu;
	
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
		/* Test */
//		devices.add(new Device("Curiosity", "localhost", 1234));
		/**/
		
		menu = ConsoleMenu.getInstance(this);
		menu.awaitCommands();
		
	}
	
	public void onAddCommand(MenuOption option) {
		String inputID = option.params.get(0);
		Device found = getDeviceById(inputID);
		if (found == null) {
			devices.add(new Device(inputID, "localhost", 1234)); // TODO url and port
		}
		else {
			System.out.println("Device with ID " + inputID + " already exists!");
		}
	}
	
	private Device getDeviceById(String id) {
		return devices.stream().filter(d -> d.getID().equals(id)).findAny().orElse(null);
	}
	
	public void onRemoveCommand(MenuOption option) {
		String inputID = option.params.get(0);
		Device found = getDeviceById(inputID);
		if (found != null) {
			devices.remove(found);
		}
		else {
			System.out.println("Device with ID " + inputID + " was not found!");
		}
	}
	
	public void connect(MenuOption option) {
		String inputID = option.params.get(0);
		Device addedDevice = getDeviceById(inputID);
		
		if (addedDevice != null) {
			ArrayList<String> remoteCommands = transmitter.connectWith(addedDevice);
			// TODO test connection lost with the rover and remove commands on disconnect
			if (remoteCommands != null && remoteCommands.size() > 0) {
	//			devices.add(device);
				menu.onDeviceConnected(devices.get(0).getID(), remoteCommands);
			}
			else {
				// TODO
			}
			
		}
		else {
			System.out.println("Device with ID " + option.params.get(0) + " does not exist!");
		}
	}
	
	private boolean deviceExists(String deviceName) {
		return devices.stream().anyMatch(d-> d.getID().equals(deviceName));
	}
	
	public void disconnect() {
		if (devices.size() == 0) {
			System.out.println("There are no connected devices.");
		}
		else if (transmitter.disconnectWith(devices.get(0))) { // TODO try-catch?
			devices.remove(devices.get(0));
			menu.onDeviceDisconnected(devices.get(0).getID());
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

