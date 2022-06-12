package com.spaceagency.commandcenter;

import com.spaceagency.common.Command;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Transmitter {
	private Socket clientSocket;
//    private ObjectOutputStream out;
    private PrintWriter out;
    private BufferedReader commandResponse;
	private ObjectInputStream in;

    public Map<String, ArrayList<String>> connectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		Map<String, ArrayList<String>> remoteCommands = null;
		
		try {
			clientSocket = new Socket(url, port);
			OutputStream outputStream = clientSocket.getOutputStream();
//			out = new ObjectOutputStream(outputStream);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			commandResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.printf("Connected to %s:%s.%nMore commands available.%n", url, port);
			
			remoteCommands = (Map<String, ArrayList<String>>) in.readObject();
			
//			System.out.println(remoteCommands.toString()); // TODO: get commands and save them. remove commands on disconnect
		} catch (IOException | ClassNotFoundException e) {
			System.out.printf("Could not connected to %s:%s%n", url, port);
			// todo: more info
			e.printStackTrace();
		}
		
		return remoteCommands;
    }
	
	public String sendCommand(String command) {
		String resp = null;
		try {
//			out.writeObject(command);
			out.println(command);
			resp = commandResponse.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}

//    public String sendMessage(String msg) {
//        out.println(msg);
//		String resp = null;
//		try {
//			resp = in.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return resp;
//    }

    public void disconnectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		
		try {
			if (in != null) {
				in.close();
				out.close();
				clientSocket.close();
				System.out.printf("Disconnected from %s:%s%n", url, port);
			}
			else {
				System.out.printf("Already disconnected from %s:%s%n", url, port);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.printf("Could not disconnect from %s:%s%n", url, port);
		}
    }
	
//	@Test
//	public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
//		System.out.println("Test...");
//		Transmitter client = new Transmitter();
//		client.startConnection("127.0.0.1", 1234);
//		String response = client.sendMessage("hello server");
//		System.out.println("response " + response);
////		assertEquals("hello client", response);
//	}
}
