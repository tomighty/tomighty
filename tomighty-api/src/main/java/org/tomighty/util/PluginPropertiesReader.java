/*
 * Copyright (c) 2010-2012 Célio Cidral Junior, Dominik Obermaier.
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */

package org.tomighty.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author dobermai
 */
public class PluginPropertiesReader {

    private static final String TOMIGHTY_PLUGIN_PROPERTIES = "tomighty-plugin.properties";

    public String getPluginClassName(ClassLoader classLoader) {
        Properties properties = loadProperties(classLoader);
        return properties.getProperty("class");
    }

    public String getGuiceModuleClassName(ClassLoader classLoader) {
        Properties properties = loadProperties(classLoader);

        return properties.getProperty("guice.module");
    }

    public String getPluginName(ClassLoader classLoader) {
        Properties properties = loadProperties(classLoader);

        return properties.getProperty("name");
    }

    public String getPluginVersion(ClassLoader classLoader) {
        Properties properties = loadProperties(classLoader);

        return properties.getProperty("version");
    }


    private Properties loadProperties(ClassLoader classLoader) {
        InputStream inputStream = classLoader.getResourceAsStream(TOMIGHTY_PLUGIN_PROPERTIES);

        if (inputStream == null)
            throw new IllegalArgumentException("Input stream must not be null");

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
