package com.spaceagency.rover.instruments;

import com.spaceagency.rover.interfaces.RemoteCommand;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Camera extends ElectricalInstrument {
	
	private static String nasaUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera=navcam&api_key=DEMO_KEY";
	
	public Camera(int consumedPower, Battery battery) {
		super(consumedPower, battery);
	}
	
	@RemoteCommand
	public String takePhoto() {
		return getJSON();
	}
	
	public String getJSON() {
 		HttpClient client = HttpClient.newHttpClient();
    	HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(nasaUrl))
          .build();
		
		HttpResponse<String> response = null;
		
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		return response.body();
    }
	
	@Override
	public boolean isOperational() {
		return true;
	}
}
