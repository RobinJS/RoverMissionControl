package com.spaceagency.commandcenter.menu;

import java.util.Scanner;

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
