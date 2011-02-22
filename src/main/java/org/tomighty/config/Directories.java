package org.tomighty.config;

import java.io.File;

public class Directories {
	
	private File configuration;
	
	public Directories() {
		File userHome = new File(System.getProperty("user.home"));
		configuration = new File(userHome, ".tomighty");
		configuration.mkdirs();
	}

	public File configuration() {
		return configuration;
	}

}
