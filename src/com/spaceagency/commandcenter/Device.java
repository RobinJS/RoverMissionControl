package com.spaceagency.commandcenter;

public class Device {
	private final String ID;
	private final String url;
	private final int port;
	
	public Device(String ID, String url, int port) {
		this.ID = ID;
		this.url = url;
		this.port = port;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getPort() {
		return port;
	}
}
