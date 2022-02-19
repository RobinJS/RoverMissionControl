package com.spaceagency;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.commandcenter.menu.Menu;
import com.spaceagency.rover.Rover;
import com.spaceagency.utils.Direction;
import com.spaceagency.utils.Position;

import java.util.Scanner;

public class MissionControl {

    public static void main(String[] args) {
        CommandCenter commandCenter = CommandCenter.getInstance();
    
        Rover curiosity = new Rover("Curiosity", new Position(0, 0), Direction.EAST);
        
        commandCenter.addDevice(curiosity);
        
        // to do: replace String with Enum or other type.
//        String connectionStatus = commandCenter.connectTo(curiosity);
//        if (Objects.equals(connectionStatus, "SUCCESS")) {
//            // to do: now you have more options available
//            System.out.println("Connected to...");
//        }
    
        Menu menu = Menu.getInstance(commandCenter);
    
    
//        UserMenu userMenu = new UserMenu();

//        System.out.println(curiosity.getStatus());
        
    }
}
