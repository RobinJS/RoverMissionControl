package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.devices.Device;
import com.spaceagency.common.MenuOption;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class Transmitter {
	private Socket socket;
	private ObjectOutputStream out;
	private BufferedReader commandResponse;
	private ObjectInputStream in;
	
	
	public ArrayList<String> connectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		ArrayList<String> remoteCommands = null;
		
		try {
			socket = new Socket(url, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			commandResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.printf("Connected to %s:%s.%n", url, port);
			
			remoteCommands = (ArrayList<String>) in.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			System.out.printf("Could not connected to %s:%s. Remote server may not be running.%n", url, port);
		}
		
		return remoteCommands;
	}
	
	public String sendCommand(MenuOption command) {
		String resp = null;
		try {
			out.writeObject(command);
			resp = commandResponse.readLine();
		} catch (IOException e) {
		
		}
		return resp;
	}
	
	public boolean disconnectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		
		try {
			socket.close();
			in.close();
			out.close();
			commandResponse.close();
		} catch (IOException e) {
			System.out.printf("Could not disconnect from %s:%s%n", url, port);
			e.printStackTrace();
		}
		
		boolean success = false;
		if (in != null) {
			System.out.printf("Disconnected from %s:%s%n", url, port);
			success = true;
		}
		else System.out.printf("Already disconnected from %s:%s%n", url, port);
		
		return  success;
	}
}
