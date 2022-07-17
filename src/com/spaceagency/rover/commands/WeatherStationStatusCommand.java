package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.WeatherStation;

public class WeatherStationStatusCommand implements Command {
	WeatherStation weatherStation;
	
	public WeatherStationStatusCommand(WeatherStation weatherStation) {
		this.weatherStation = weatherStation;
	}
	
	public String execute() {
		return weatherStation.getWeather();
	}
}
