package com.spaceagency.rover.instruments;

import com.spaceagency.rover.CommandExecutor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

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
        private final Socket clientSocket;
		//		private CommandExecutor commandExecutor;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
//			this.commandExecutor = commandExecutor;
        }

        public void run() {
			try {
				InputStream inputStream = clientSocket.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter commandResponse = new PrintWriter(clientSocket.getOutputStream(), true);
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				
				System.out.println("New connection registered.");
//				out.println(commandExecutor.getRemoteCommands());
				Map<String, ArrayList<String>> commands = commandExecutor.getRemoteCommands();
				out.writeObject(commands);
				
				String input;
				while ((input = in.readLine()) != null) {
					String response = commandExecutor.runCommand(input);
					commandResponse.println(response);
					// TODO maybe send JSON String and parse it
				}
				
				in.close();
				out.close();
				commandResponse.close();
				clientSocket.close();
				System.out.println("client socket closed");
			} catch (IOException e) {
				System.out.println("Lost connection with Command Center.");
//				e.printStackTrace();
			}
		}
	}
}
