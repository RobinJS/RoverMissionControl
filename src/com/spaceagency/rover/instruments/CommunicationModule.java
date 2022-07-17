package com.spaceagency.rover.instruments;

import com.spaceagency.rover.commands.CommandExecutor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Objects;

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
			BufferedReader in = null;
			ObjectOutputStream out = null;
			PrintWriter commandResponse = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				commandResponse = new PrintWriter(clientSocket.getOutputStream(), true);
				
				System.out.println("New connection registered.");
//				out.println(commandExecutor.getRemoteCommands());
				ArrayList<String> commands = commandExecutor.getRemoteCommands();
				out.writeObject(commands);
				
				String input;
				while ((input = in.readLine()) != null) {
					String response = commandExecutor.runCommand(input);
					commandResponse.println(response);
				}
				
			} catch (IOException e) {
				System.out.println("Lost connection with Command Center.");
//				e.printStackTrace();
			}
			finally {
				try {
					Objects.requireNonNull(in).close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					Objects.requireNonNull(out).close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Objects.requireNonNull(commandResponse).close();
				
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("client socket closed");
				
			}
		}
	}
}
