package com.spaceagency.rover.instruments;

import com.spaceagency.common.MenuOption;
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
			while (!serverSocket.isClosed()) new ClientHandler(serverSocket.accept()).start();
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
        private final Socket handledSocket;

        public ClientHandler(Socket socket) {
            this.handledSocket = socket;
        }

        public void run() {
			ObjectInputStream in = null;
			ObjectOutputStream out = null;
			PrintWriter commandResponse = null;
			
			try {
				out = new ObjectOutputStream(handledSocket.getOutputStream());
				in = new ObjectInputStream(handledSocket.getInputStream());
				commandResponse = new PrintWriter(handledSocket.getOutputStream(), true);
				
				System.out.println("New connection registered.");
				ArrayList<String> commands = commandExecutor.getRemoteCommands();
				out.writeObject(commands);
				
				MenuOption input;
				while ((input = (MenuOption) in.readObject()) != null) {
					String response = commandExecutor.runCommand(input);
					commandResponse.println(response);
				}
				
			} catch (IOException e) {
				System.out.println("Lost connection with Command Center.");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					Objects.requireNonNull(in).close();
					Objects.requireNonNull(out).close();
					Objects.requireNonNull(commandResponse).close();
					handledSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("client socket closed");
				
			}
		}
	}
}
