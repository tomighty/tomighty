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

package org.tomighty.config;

import java.io.File;
import java.util.Properties;

import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;

public class Configuration implements Initializable {
	
	private Properties properties;
	private File configDir;
	private File configFile;
	@Inject PropertyStore propertyStore;
	@Inject @New Log log;
	
	@Override
	public void initialize() {
		File userHome = new File(System.getProperty("user.home"));
		configDir = new File(userHome, ".tomighty");
		configFile = new File(configDir, "tomighty.conf");
		if(configDir.exists() && configDir.isFile()) {
			try {
				properties = propertyStore.load(configDir);
			} catch(Exception e) {
				log.error("Error importing old config file", e);
			}
			configDir.delete();
			saveConfiguration();
		} else {
			properties = propertyStore.load(configFile);
		}
	}
	
	public int asInt(String name, int defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Integer.parseInt(value);
	}
	
	public boolean asBoolean(String name, boolean defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public void set(String name, boolean value) {
		set(name, String.valueOf(value));
	}

	public void set(String name, int value) {
		set(name, String.valueOf(value));
	}

	private void set(String name, String value) {
		properties.setProperty(name, value);
		saveConfiguration();
	}

	private void saveConfiguration() {
		if(!configDir.exists()) {
			configDir.mkdirs();
		}
		propertyStore.store(properties, configFile);
	}
	
}
