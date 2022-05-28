package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.Transmitter;
import com.spaceagency.common.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	Scanner in;
	
	private ConsoleMenu() { }
	
	public static ConsoleMenu getInstance() {
		if (instance == null) {
			instance = new ConsoleMenu();
			instance.init();
		}
		return instance;
	}
	
	private void init() {
		in = new Scanner(System.in);
		System.out.println("Enter 'help' to see available commands.");
		
		/*CommandCenter at = commandCenter;
        for (Method m : at.getClass().getMethods()) {
           MenuItem mXY = (MenuItem)m.getAnnotation(MenuItem.class);
           if (mXY != null) {
			   System.out.println("method: " + m.getName());
           }
        }*/
	}
	
	public String getNextCommand() {
		return in.nextLine();
	}
	
	
}
