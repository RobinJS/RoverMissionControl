package com.example.rover.instruments;

import com.example.rover.Message;
import org.springframework.web.bind.annotation.GetMapping;

public class Camera extends ElectricalInstrument {
	
	public Camera() {
		super(0, null);
	}
	
	public Camera(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@Override
	public boolean isOperational() {
		return true;
	}
	
	@GetMapping("camera/getImages")
	public Message getImages() {
		
		return new Message("{\"name\": \"Pesho\"}");
		
		/* to do
		try	{
			battery.consume(consumedPower);
			
			try {
				String msg = getImageDataFromURL();
				return new Message(msg);
			} catch (Exception e) {
				return new Message(e.getMessage());
			}
		} catch (NotEnoughPowerException e) {
			return new Message("Cannot get images. " + e.getMessage());
		}
		*/
	}
	
	private String getImageDataFromURL() {
		// https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=DEMO_KEY
		return "{}";
	}
}
