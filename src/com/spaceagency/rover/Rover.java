package com.spaceagency.rover;

import com.spaceagency.interfaces.Device;

public class Rover implements Device {
	String id;
	
	public String getId() {
		return id;
	}
	
	public Rover(String id) {
		this.id = id;
	}
}
