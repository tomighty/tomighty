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

package org.tomighty.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;

public class Messages {
	
	private final Properties messages = new Properties();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Messages() {
		logger.info("Loading messages for locale " + locale());
		InputStream input = getClass().getResourceAsStream(resourceName());
		if(input == null) {
            logger.info("Messages not available for locale " + locale() + ".");
		} else {
			try {
				loadMessagesFrom(input);
			} catch (IOException e) {
                logger.error("Error while loading resource: " + resourceName(), e);
			}
		}
	}

	public String get(String name) {
		String key = name.replace(' ', '.');
		if(messages.containsKey(key)) {
			return messages.getProperty(key);
		}
		return name;
	}

	private void loadMessagesFrom(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input, "utf-8");
		try {
			messages.load(reader);
		} finally {
			reader.close();
		}
	}

	private String locale() {
		Locale locale = Locale.getDefault();
		return locale.getLanguage()+"_"+locale.getCountry();
	}

	private String resourceName() {
		return "/messages_"+locale()+".properties";
	}

}
