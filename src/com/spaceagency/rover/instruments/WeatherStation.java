package com.spaceagency.rover.instruments;

import com.spaceagency.rover.interfaces.RemoteCommand;

import java.util.Locale;
import java.util.Random;

public class WeatherStation extends ElectricalInstrument {
	
	public WeatherStation(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@RemoteCommand
	public String weather() {
		String status = "unknown";
		
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "Temperature: " + getTemperatureInCelsius() + "C, humidity: " + getHumidity() + "%.";
		}
		else {
			System.out.println("Cannot get Environment status. Not enough power.");
		}
		
		return status;
	}
	
	public float getTemperatureInCelsius() {
		float min = -128f;
		float max = 21f;
		float temp = min + new Random().nextFloat() * (max - min);
		String str = String.format(Locale.US, "%.2f", temp);
		return Float.parseFloat(str);
	}
	
	public int getHumidity() {
		return new Random().nextInt(50) + 50;
	}
	
	@Override
	public boolean isOperational() {
		return false;
	}
}
