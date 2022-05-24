package com.spaceagency.rover.instruments;

import java.util.Locale;
import java.util.Random;

public class WeatherStation extends ElectricalInstrument {
	
	public WeatherStation(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		String status = "uknown";
		
		if (battery.hasPower(consumedPower)) {
			battery.consume(consumedPower);
			status = "Environment - temp: " + getTemperatureInCelsius() + "C, humidity: " + getHumidity();
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
		String str = String.format(Locale.US, "%.2f", 55.25);
		float result = Float.parseFloat(str);
		return result;
	}
	
	public int getHumidity() {
		return new Random().nextInt(100-50) + 50;
	}
	
	@Override
	public boolean isOperational() {
		return false;
	}
}