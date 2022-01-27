package com.spaceagency.interfaces;

public interface CommandCenter {
	void addDevice(Device device);
	void connectTo(Device device);
	void disconnectFrom(Device device);
}
