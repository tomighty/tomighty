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

import org.tomighty.ioc.Container;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;

public class Configuration implements Initializable {
	
	@Inject private Directories directories;
	@Inject private PropertyStore propertyStore;
	@Inject private Container container;
	private Properties properties;
	private File configFile;
	
	@Override
	public void initialize() {
		configFile = new File(directories.configuration(), "tomighty.conf");
		properties = propertyStore.load(configFile);
	}

	public int asInt(String name, int defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Integer.parseInt(value);
	}
	
	public boolean asBoolean(String name, boolean defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T asObject(String name, Class<T> defaultClass) {
		String className = properties.getProperty(name);
		if(className == null) {
			return container.get(defaultClass);
		}
		return (T) container.get(className);
	}

	public void set(String name, boolean value) {
		set(name, String.valueOf(value));
	}

	public void set(String name, int value) {
		set(name, String.valueOf(value));
	}

	public void set(String name, Class<?> value) {
		set(name, value.getName());
	}

	private void set(String name, String value) {
		properties.setProperty(name, value);
		saveConfiguration();
	}

	private void saveConfiguration() {
		propertyStore.store(properties, configFile);
	}
	
}
