/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.config;

import org.tomighty.log.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyStore {

    private final Log log = new Log(getClass());

	public Properties load(File file) {
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
				log.warn("Error while loading properties file: "+file, e);
			}
		}
		return properties;
	}
	
	public void store(Properties properties, File file) {
		try {
			FileWriter writer = new FileWriter(file);
			try {
				properties.store(writer, null);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			log.warn("Error while storing properties file: "+file, e);
		}
	}

}
