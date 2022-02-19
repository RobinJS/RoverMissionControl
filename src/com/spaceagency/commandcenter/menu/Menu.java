package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.interfaces.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Menu {
	private static Menu instance;
	private static Command connectCommand;
	private static CommandCenter commandCenter;
	
	private Menu(CommandCenter commandCenter) {
		this.commandCenter = commandCenter;
	}
	
	public static Menu getInstance(CommandCenter commandCenter) {
		if (instance == null) {
			instance = new Menu(commandCenter);
			init();
		}
		return instance;
	}
	
	private static void init() {
		connectCommand = new ConnectCommand(commandCenter);
		
		Scanner in = new Scanner(System.in);
        String input = "";
		
		List<String> commands = new ArrayList<String>(Arrays.asList("exit", "help", "connect"));
		
		System.out.println("Enter 'help' to see available commands.");
		
        /*while (!input.equals("exit")) {
            input = in.nextLine();
			
			switch (input) {
				case "exit": System.out.println("Exiting program..."); break;
				case "help": printAllCommands(commands); break;
				case "connect": connectCommand.execute(); break;
				default:
					System.out.println("Invalid command.");
			}
    
//            System.out.println(s);
        }*/
		
		
		CommandCenter at = commandCenter;
        for (Method m : at.getClass().getMethods()) {
           MenuItem mXY = (MenuItem)m.getAnnotation(MenuItem.class);
           if (mXY != null) {
			   System.out.println("method: " + m.getName());
           }
        }
	}
	
	private static void printAllCommands(List<String> commands) {
		for (String c : commands) {
			System.out.print(c + " ");
		}
		System.out.println("");
	}
}
