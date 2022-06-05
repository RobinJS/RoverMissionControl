package com.spaceagency.commandcenter.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
	private static ConsoleMenu instance;
	private	Scanner in;
	
	List<String> commands = new ArrayList<String>(Arrays.asList("help", "connect", "disconnect"));
	
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
	}
	
	public String getNextCommand() {
		return validateCommand(in.nextLine());
	}
	
	private String validateCommand(String command) {
		// TODO: validate!
		return command;
	}
	
	public void printAllCommands() {
		
		/*CommandCenter at = commandCenter;
        for (Method m : at.getClass().getMethods()) {
           MenuItem mXY = (MenuItem)m.getAnnotation(MenuItem.class);
           if (mXY != null) {
			   System.out.println("method: " + m.getName());
           }
        }*/
		
		commands.forEach(c -> System.out.println(c));
	}
}
