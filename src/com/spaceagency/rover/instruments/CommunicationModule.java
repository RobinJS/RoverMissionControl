package com.spaceagency.rover.instruments;

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
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
			System.out.println("run");
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (".".equals(inputLine)) {
						out.println("bye");
						break;
					}
					out.println(inputLine);
					System.out.println("from client: " + inputLine);
				}
				
				in.close();
				out.close();
				clientSocket.close();
				System.out.println("client socket closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
