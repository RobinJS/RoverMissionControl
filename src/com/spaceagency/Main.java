package com.spaceagency;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.rover.Rover;

public class Main {

    public static void main(String[] args) {
        CommandCenter commandCenter = CommandCenter.getInstance();
    
        Rover curiosity = new Rover("Curiosity");
        
        commandCenter.addDevice(curiosity);
        commandCenter.connectTo(curiosity.getId());
        // test
    }
}
