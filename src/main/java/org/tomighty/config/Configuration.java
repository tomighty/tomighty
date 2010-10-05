/*
Copyright 2010 Célio Cidral Junior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.tomighty.config;

import java.io.File;
import java.util.Properties;

import org.tomighty.util.Props;

public class Configuration {
	
	private static final File CONFIG_FILE = new File(System.getProperty("user.home") + "/.tomighty");
	private final Properties properties = Props.load(CONFIG_FILE);
	
	public boolean asBoolean(String name, boolean defaultValue) {
		String value = properties.getProperty(name);
		return value == null ? defaultValue : Boolean.parseBoolean(value);
	}

	public void set(String name, boolean value) {
		set(name, String.valueOf(value));
	}

	private void set(String name, String value) {
		properties.setProperty(name, value);
		Props.store(properties, CONFIG_FILE);
	}
	
}
