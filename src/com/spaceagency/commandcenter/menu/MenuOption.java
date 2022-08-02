package com.spaceagency.commandcenter.menu;

import java.util.List;

public class MenuOption {
	public String command;
	public List<String> params;
	public String requiredParams;
	
	public MenuOption(String command, String requiredParams, List<String> params) {
		this.command = command;
		this.requiredParams = requiredParams;
		this.params = params;
	}
	
	public MenuOption(String command, String requiredParams) {
		this.command = command;
		this.requiredParams = requiredParams;
	}
	
	public MenuOption(String command) {
		this.command = command;
	}
}
