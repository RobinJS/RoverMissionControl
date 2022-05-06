package com.example.rover;

//import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoverApplication.class, args);
		
		// Rover curiosity = new Rover("Curiosity", new Position(0, 0), Direction.EAST);
	}

}
