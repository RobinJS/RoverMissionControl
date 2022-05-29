package com.spaceagency.rover.instruments;

import com.spaceagency.common.Command;
import com.spaceagency.rover.CommandExecutor;

import java.io.*;
import java.net.*;

public class CommunicationModule {
	private ServerSocket serverSocket;
	private static CommandExecutor commandExecutor;
	
	public  CommunicationModule(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}
	
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
//		private CommandExecutor commandExecutor;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
//			this.commandExecutor = commandExecutor;
        }

        public void run() {
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				InputStream inputStream = clientSocket.getInputStream();
				in = new ObjectInputStream(inputStream);
				
				System.out.println("New connection.");
				
				Command command;
				while ((command = (Command) in.readObject()) != null) {
					String response = commandExecutor.runCommand(command);
//
					out.println("recieved command " + response.toString());
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
