package com.spaceagency.commandcenter.menu;

import com.spaceagency.commandcenter.CommandCenter;
import com.spaceagency.interfaces.Command;
import com.spaceagency.interfaces.Device;

public class ConnectCommand implements Command {
	private CommandCenter commandCenter;
	
	public ConnectCommand(CommandCenter commandCenter) {
		this.commandCenter = commandCenter;
	}
	
	@Override
	public void execute() {
		commandCenter.connect(null);
	}
}
