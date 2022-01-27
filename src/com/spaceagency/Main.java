package com.spaceagency;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.rover.Rover;
import com.spaceagency.satellite.Satellite;
import com.spaceagency.utils.Direction;
import com.spaceagency.utils.Position;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        CommandCenter commandCenter = CommandCenter.getInstance();
    
        Rover curiosity = new Rover("Curiosity", new Position(0, 0), Direction.EAST);
        Satellite comSat = new Satellite("ComSat");
        
        commandCenter.addDevice(curiosity);
        commandCenter.addDevice(comSat);
        
        // to do: replace String with Enum or other type.
        // Move communication to another module. Satellite.
        // Ask satellite to connect to rover
        String connectionStatus = commandCenter.connectTo(curiosity);
        
        if (Objects.equals(connectionStatus, "SUCCESS")) {
            // to do: now you have more options available
            System.out.println("Connected to...");
        }
    }
}
