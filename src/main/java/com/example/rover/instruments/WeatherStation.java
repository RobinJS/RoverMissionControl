package com.example.rover.instruments;

import com.example.rover.utils.exceptions.NotEnoughPowerException;

import java.util.Locale;
import java.util.Random;

public class WeatherStation extends ElectricalInstrument {
	
	public WeatherStation(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public String getStatus() {
		String message = "uknown";
		
		try	{
			battery.consume(consumedPower);
			message = "Environment - temp: " + getTemperatureInCelsius() + "C, humidity: " + getHumidity();
		} catch (NotEnoughPowerException e) {
			message = "Cannot get environment status. " + e.getMessage();
		}
		
		return message;
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
		return new Random().nextInt(50) + 50;
	}
	
	@Override
	public boolean isOperational() {
		return false;
	}
}
