package com.spaceagency.rover.commands;

import com.spaceagency.rover.instruments.Camera;

public class CameraTakePhotoCommand implements Command {
	Camera camera;
	
	public CameraTakePhotoCommand(Camera camera) {
		this.camera = camera;
	}
	
	public String execute() {
		return camera.takePhoto();
	}
}
