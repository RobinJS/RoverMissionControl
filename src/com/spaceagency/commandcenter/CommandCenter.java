package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.devices.Device;
import com.spaceagency.commandcenter.menu.ConsoleMenu;
import com.spaceagency.common.MenuOption;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CommandCenter {
	private final Transmitter transmitter;
	private final ArrayList<Device> devices = new ArrayList<>();
	private Device connectedDevice;
	private final ConsoleMenu menu;
	
	public static void main(String[] args) {
        CommandCenter commandCenter = new CommandCenter();
	}
	
	private CommandCenter() {
		transmitter = new Transmitter();
		menu = ConsoleMenu.getInstance(this);
		menu.awaitCommands();
	}
	// TODO unit tests
	public void onAddCommand(MenuOption option) {
		String inputID = option.params.get(0);
		Device found = getDeviceById(inputID);
		if (found == null) {
			devices.add(new Device(inputID, "localhost", 1234));
			System.out.println("New device added: " + inputID);
		}
		else {
			System.out.println("Device with ID " + inputID + " already exists.");
		}
	}
	
	private Device getDeviceById(String id) {
		return devices.stream().filter(d -> d.getID().equals(id)).findAny().orElse(null);
	}
	
	public void onRemoveCommand(MenuOption option) {
		String inputID = option.params.get(0);
		Device found = getDeviceById(inputID);
		if (found != null) {
			removeDevice(found);
			System.out.println("Device removed: " + inputID);
		}
		else {
			System.out.println("Device with ID " + inputID + " was not found.");
		}
	}
	
	private void removeDevice(Device device) {
		devices.remove(device);
	}
	
	public void connect(MenuOption option) {
		String inputID = option.params.get(0);
		Device addedDevice = getDeviceById(inputID);
		
		if (addedDevice != null) {
			ArrayList<String> remoteCommands = transmitter.connectWith(addedDevice);
			
			if (remoteCommands != null && remoteCommands.size() > 0) {
				connectedDevice = addedDevice;
				menu.onDeviceConnected(devices.get(0).getID(), remoteCommands);
			}
			
		}
		else {
			System.out.println("Device with ID " + option.params.get(0) + " does not exist.");
		}
	}
	
	private boolean deviceExists(String deviceName) {
		return devices.stream().anyMatch(d-> d.getID().equals(deviceName));
	}
	
	public void disconnect() {
		if (devices.size() == 0) {
			System.out.println("There are no connected devices.");
		}
		else if (transmitter.disconnectWith(connectedDevice)) {
			menu.onDeviceDisconnected(connectedDevice.getID());
			connectedDevice = null;
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
	
	public void sendCommand(MenuOption command) {
		if (devices.size() > 0) {
			String response = transmitter.sendCommand(command);
			if (response == null) {
				System.out.println("Lost connection with " + connectedDevice.getID());
				menu.onDeviceDisconnected(connectedDevice.getID());
				connectedDevice = null;
			}
			else {
				System.out.println("Response: " + response);
			}
		}
	}
	
	
}

