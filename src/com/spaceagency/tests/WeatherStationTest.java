package com.spaceagency.tests;

import com.spaceagency.rover.instruments.WeatherStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherStationTest {
	
	@Test
	void humidityValueCheck() {
		var weatherStation = new WeatherStation(1, null);
		int humidity = weatherStation.getHumidity();
		assertTrue(humidity >= 50 && humidity < 100);
	}
	
	
	@Test
	void temperatureInCelsiusValueCheck() {
		var weatherStation = new WeatherStation(1, null);
		float temp = weatherStation.getTemperatureInCelsius();
		System.out.println(temp);
		assertTrue(temp >= -128f && temp < 21f);
	}
}