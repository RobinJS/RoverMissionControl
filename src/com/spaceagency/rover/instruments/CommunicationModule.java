package com.spaceagency.rover.instruments;

import com.spaceagency.common.Command;

import java.io.*;
import java.net.*;

public class CommunicationModule {
	private ServerSocket serverSocket;
	
	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
        		while (true) new EchoClientHandler(serverSocket.accept()).start();
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
	
	private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private ObjectInputStream in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
			System.out.println("run");
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				InputStream inputStream = clientSocket.getInputStream();
				in = new ObjectInputStream(inputStream);
				
				Command command = (Command) in.readObject();
				System.out.println("recieved command " + command.toString());
//				while ((command = (Command) in.readObject()) != null) {
//
//					out.println("recieved command " + command.toString());
//					System.out.println("from client: " + "");
//				}
				
				in.close();
				out.close();
				clientSocket.close();
				System.out.println("client socket closed");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
    }
}
