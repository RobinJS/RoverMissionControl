package com.spaceagency.rover.instruments;

import com.spaceagency.common.Command;

import java.io.*;
import java.net.*;

public class CommunicationModule {
	private ServerSocket serverSocket;
	
	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
        		while (true) new ClientHandler(serverSocket.accept()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				InputStream inputStream = clientSocket.getInputStream();
				in = new ObjectInputStream(inputStream);
				
				System.out.println("New connection.");
				
				Command command;
				while ((command = (Command) in.readObject()) != null) {

					System.out.println("recieved command " + command.getExecutorName() + " " + command.getCommandName());
					out.println("recieved command " + command.toString());
				}
				
				in.close();
				out.close();
				clientSocket.close();
				System.out.println("client socket closed");
			} catch (IOException e) {
				System.out.println("Lost connection with Command Center.");
//				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Unidentified command.");
				e.printStackTrace();
			}
		}
    }
}
