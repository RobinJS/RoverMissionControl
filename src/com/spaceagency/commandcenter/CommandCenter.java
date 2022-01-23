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
	
	public void connectTo(String id) {
		// to do: stream online connection. Listen for connection lost
		if (devices.containsKey(id)) {
			System.out.println(String.format("Connecting to %s. Please, standby... ", id));
		}
		else {
			System.out.println(String.format("Id %s not registered.", id));
		}
	}
}
