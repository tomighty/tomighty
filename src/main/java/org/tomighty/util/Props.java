package org.tomighty.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Props {
	
	private static final Logger LOGGER = Logger.getLogger(Props.class.getName());

	public static Properties load(File file) {
		Properties properties = new Properties();
		if(file.exists()) {
			try {
				FileReader reader = new FileReader(file);
				try {
					properties.load(reader);
				} finally {
					reader.close();
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Error while loading properties file: "+file, e);
			}
		}
		return properties;
	}
	
	public static void store(Properties properties, File file) {
		try {
			FileWriter writer = new FileWriter(file);
			try {
				properties.store(writer, null);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error while storing properties file: "+file, e);
		}
	}
}
