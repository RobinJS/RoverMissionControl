package com.spaceagency.commandcenter;

import com.spaceagency.commandcenter.Command;
import org.junit.Test;
import java.io.*;
import java.net.*;

import static org.junit.Assert.assertEquals;

public class Transmitter {
	private Socket clientSocket;
    private ObjectOutputStream out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
			OutputStream outputStream = clientSocket.getOutputStream();
			out = new ObjectOutputStream(outputStream);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println(String.format("Connected to %s:%s", ip, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public String sendCommand(Command command) {
		String resp = null;
		try {
			out.writeObject(command);
			resp = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}

    public String sendMessage(String msg) {
        out.println(msg);
		String resp = null;
		try {
			resp = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
    }

    public void stopConnection() {
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Test
	public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
		System.out.println("Test...");
		Transmitter client = new Transmitter();
		client.startConnection("127.0.0.1", 1234);
		String response = client.sendMessage("hello server");
		System.out.println("response " + response);
//		assertEquals("hello client", response);
	}
}
