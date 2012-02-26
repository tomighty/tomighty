/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	
	private final Logger logger;
	
	public Log(Class<?> clazz) {
		this.logger = Logger.getLogger(clazz.getName());
	}

	public void info(String msg) {
		logger.log(Level.INFO, msg);
	}
	
	public void error(String msg, Throwable error) {
		logger.log(Level.SEVERE, msg, error);
	}

	public void warn(String msg, Throwable error) {
		logger.log(Level.WARNING, msg, error);
	}
}
