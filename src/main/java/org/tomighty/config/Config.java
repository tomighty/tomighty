package org.tomighty.config;

import java.io.File;
import java.util.Properties;

import org.tomighty.util.Props;

public class Config {
	
	private static final File CONFIG_FILE = new File(System.getProperty("user.home") + "/.tomighty");
	private static Properties properties;
	
	static {
		properties = Props.load(CONFIG_FILE);
	}
	
	public static boolean asBoolean(String name, boolean defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public static void set(String name, boolean value) {
		set(name, String.valueOf(value));
	}

	private static void set(String name, String value) {
		properties.setProperty(name, value);
		Props.store(properties, CONFIG_FILE);
	}
	
}
