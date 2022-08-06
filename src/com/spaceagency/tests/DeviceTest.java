package com.spaceagency.tests;

import com.spaceagency.commandcenter.devices.Device;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTest {
	
	@Test
	void getID() {
		var d = new Device("Curiosity", "localhost", 8080);
		assertEquals("Curiosity", d.getID());
	}
	
	@Test
	void getUrl() {
		var d = new Device("Curiosity", "localhost", 8080);
		assertEquals("localhost", d.getUrl());
	}
	
	@Test
	void getPort() {
		var d = new Device("Curiosity", "localhost", 8080);
		assertEquals(8080, d.getPort());
	}
}