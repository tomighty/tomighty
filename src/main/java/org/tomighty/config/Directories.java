package org.tomighty.config;

import org.tomighty.io.FileSystemDirectory;
import org.tomighty.io.Directory;

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

    public Directory plugins() {
        File path = new File(configuration(), "plugins");
        Directory directory = new FileSystemDirectory(path);
        directory.create();
        return directory;
    }

}
