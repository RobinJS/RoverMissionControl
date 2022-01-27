package com.spaceagency.satellite;

import com.spaceagency.interfaces.Device;

public class Satellite implements Device {
	String id;
	
	public String getId() { return this.id; }
	
	public Satellite(String id) {
		this.id = id;
	}
}
