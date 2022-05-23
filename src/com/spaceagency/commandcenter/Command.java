package com.spaceagency.commandcenter;

import java.io.Serializable;

public class Command implements Serializable {
	private final String text;
	
	public Command(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
