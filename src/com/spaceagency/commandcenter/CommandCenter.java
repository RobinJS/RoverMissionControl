package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.menu.MenuItem;
import com.spaceagency.common.Command;
import com.spaceagency.rover.interfaces.Device;

import java.util.HashMap;

public class CommandCenter {
	private static CommandCenter instance;
	private HashMap<String, Device> devices = new HashMap<>();
	private static Transmitter client;
	
	public static void main(String[] args) {
        CommandCenter commandCenter = new CommandCenter();
		
//		commandCenter.addDevice(curiosity);
//		Menu menu = Menu.getInstance(commandCenter);
		
		client = new Transmitter();
		client.startConnection("localhost", 1234);
		System.out.println("Command center transmitting...");
// 		String response = client.sendMessage("hello server 2");
		String response = client.sendCommand(new Command("rover", "getstatus"));
		System.out.println(response);
		
		terminateConenction(); // to do: on program close??
	}
	
	private CommandCenter() {}
	
	private static void terminateConenction() {
		client.stopConnection();
	}
	
	public void addDevice(Device device) {
		devices.put(device.getId(), device);
	}
	
	@MenuItem
	public String connect(Device device) {
		System.out.println("conecting 123 ...");
		/* to do: stream online connection. Listen for connection lost.
		* return connection status. notify on success
		* retry connection
		* stay connected to multiple devices
		* */
		return "";
		/*String id = device.getId();
		
		if (devices.containsKey(id)) {
			System.out.printf("Connecting to %s. Please, standby... %n", id);
			return "SUCCESS";
		}
		else {
			System.out.printf("Id %s not registered.%n", id);
			return "UNAVAILABLE";
		}*/
	}
	
	public void disconnect(Device device) {
	
	}
	
	public String getStatus() {
		return devices.get("Curiosity").getStatus();
	}
}

