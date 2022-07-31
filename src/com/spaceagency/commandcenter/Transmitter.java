package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.devices.Device;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class Transmitter {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader commandResponse;
	private ObjectInputStream in;
	
	
	public ArrayList<String> connectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		ArrayList<String> remoteCommands = null;
		
		try {
			clientSocket = new Socket(url, port);
			OutputStream outputStream = clientSocket.getOutputStream();
			in = new ObjectInputStream(clientSocket.getInputStream());
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			commandResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.printf("Connected to %s:%s.%nMore commands available.%n", url, port);
			
			remoteCommands = (ArrayList<String>) in.readObject();
			
//			clientSocket.

		} catch (IOException | ClassNotFoundException e) {
			System.out.printf("Could not connected to %s:%s. Remote server may not be running.%n", url, port);
//			e.printStackTrace();
		}
		
		return remoteCommands;
	}
	
	public String sendCommand(String command) {
		String resp = null;
		try {
			out.println(command);
			resp = commandResponse.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public boolean disconnectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("Could not disconnect from %s:%s%n", url, port);
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			System.out.printf("Could not disconnect from %s:%s%n", url, port);
			e.printStackTrace();
		}
		
		out.close();
		
		try {
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
