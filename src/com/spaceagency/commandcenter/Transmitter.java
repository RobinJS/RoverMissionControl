package com.spaceagency.commandcenter;

import com.spaceagency.common.Command;

import java.io.*;
import java.net.*;

import static org.junit.Assert.assertEquals;

public class Transmitter {
	private Socket clientSocket;
//    private ObjectOutputStream out;
    private PrintWriter out;
//    private BufferedReader in;
	private ObjectInputStream in;

    public void connectWith(Device device) {
		String url = device.getUrl();
		int port = device.getPort();
		
		try {
			clientSocket = new Socket(url, port);
			OutputStream outputStream = clientSocket.getOutputStream();
//			out = new ObjectOutputStream(outputStream);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
//			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.printf("Connected to %s:%s.%nMore commands available.", url, port);
			
			System.out.println(in.readObject().toString()); // TODO: get commands and save them
		} catch (IOException | ClassNotFoundException e) {
			System.out.printf("Could not connected to %s:%s%n", url, port);
			// todo: more info
			e.printStackTrace();
		}
    }
	
	public String sendCommand(String command) {
		String resp = null;
		try {
//			out.writeObject(command);
			out.println(command);
			resp = in.readLine();
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
