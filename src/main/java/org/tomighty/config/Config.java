package org.tomighty.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
	
	private static final File CONFIG_FILE = new File(System.getProperty("user.home") + "/.tomighty");
	private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
	private static Properties props = new Properties();
	
	static {
		if(CONFIG_FILE.exists()) {
			try {
				FileReader reader = new FileReader(CONFIG_FILE);
				try {
					props.load(reader);
				} finally {
					reader.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Error while loading config file", e);
			}
		}
	}
	
	public static boolean asBoolean(String name, boolean defaultValue) {
		String value = props.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public static void set(String name, boolean value) {
		set(name, String.valueOf(value));
	}

	private static void set(String name, String value) {
		props.setProperty(name, value);
		try {
			FileWriter writer = new FileWriter(CONFIG_FILE);
			try {
				props.store(writer, null);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error while storing config file", e);
		}
	}
	
}
