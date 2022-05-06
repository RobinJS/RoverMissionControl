package com.example.rover.utils.exceptions;

public class NotEnoughPowerException extends Exception {
	public String getMessage() {
		return "Not enough power.";
	}
}
