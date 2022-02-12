package com.spaceagency.instruments;

import java.util.Random;

public class WeatherStation extends ElectricalInstrument {
	
	public WeatherStation(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	public static float getTemperatureInCelsius() {
		float min = -128f;
		float max = 21f;
		float temp = min + new Random().nextFloat() * (max - min);
		String str = String.format("%.01f", temp);
		temp = Float.valueOf(str);
		
		return temp;
	}
	
	public static int getHumidity() {
		return new Random().nextInt(100-50) + 50;
	}
}
