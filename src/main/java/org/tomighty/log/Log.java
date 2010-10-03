package org.tomighty.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	
	private final Logger logger;
	
	protected Log(Class<?> clazz) {
		this.logger = Logger.getLogger(clazz.getName());
	}

	public void info(String msg) {
		logger.log(Level.INFO, msg);
	}
	
	public void error(String msg, Throwable error) {
		logger.log(Level.SEVERE, msg, error);
	}
}
