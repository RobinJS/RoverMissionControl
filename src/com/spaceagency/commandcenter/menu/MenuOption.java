package com.spaceagency.commandcenter.menu;

import java.util.List;

public class MenuOption {
	public String command;
	public List<String> params;
	
	public MenuOption(String command, List<String> params) {
		this.command = command;
		this.params = params;
	}
	
	public MenuOption(String command) {
		this.command = command;
	}
}
