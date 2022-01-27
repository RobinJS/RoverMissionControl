package com.spaceagency.commandcenter;

import com.spaceagency.interfaces.Device;

import java.util.HashMap;

public class CommandCenter {
	private static CommandCenter instance;
	HashMap<String, Device> devices = new HashMap<>();
	
	private CommandCenter() { }
	
	public static CommandCenter getInstance() {
		if (instance == null) instance = new CommandCenter();
		return instance;
	}
	
	public void addDevice(Device device) {
		devices.put(device.getId(), device);
	}
	
	public String connectTo(Device device) {
		/* to do: stream online connection. Listen for connection lost.
		* return connection status. notify on success
		* retry connection
		* stay connected to multiple devices
		* */
		String id = device.getId();
		
		if (devices.containsKey(id)) {
			System.out.printf("Connecting to %s. Please, standby... %n", id);
			return "SUCCESS";
		}
		else {
			System.out.printf("Id %s not registered.%n", id);
			return "UNAVAILABLE";
		}
	}
	
	public void disconnectFrom(Device device) {
	
	}
}

