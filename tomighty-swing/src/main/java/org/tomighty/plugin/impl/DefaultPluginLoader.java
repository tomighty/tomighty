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

package org.tomighty.plugin.impl;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginPack;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class DefaultPluginLoader implements PluginLoader {

    @Inject
    private Injector injector;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Plugin load(PluginPack pluginPack) {
        logger.info("Loading plugin {}", pluginPack);
        URLClassLoader classLoader = createClassLoader(pluginPack);
        Class<? extends Plugin> pluginClass = loadPluginClass(classLoader);

        Injector pluginInjector = createPluginInjector(classLoader);
        return pluginInjector.getInstance(pluginClass);
    }

    private Injector createPluginInjector(final URLClassLoader classLoader) {
        //FIXME: Here we have a lot to check!
        Class<? extends Module> guiceModule = getGuiceModule(classLoader);
        try {
            return injector.createChildInjector(guiceModule.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    private URLClassLoader createClassLoader(PluginPack pluginPack) {
        URL[] jars = pluginPack.jars();
        for (URL jar : jars)
            logger.info("Loading jar {}", jar);
        return new URLClassLoader(jars);
    }

    private Class<? extends Plugin> loadPluginClass(ClassLoader classLoader) {
        String pluginClassName = getPluginClassName(classLoader);
        logger.info("Loading plugin class {}", pluginClassName);
        try {
            return (Class<? extends Plugin>) classLoader.loadClass(pluginClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPluginClassName(ClassLoader classLoader) {
        InputStream inputStream = classLoader.getResourceAsStream("tomighty-plugin.properties");
        Properties properties = loadProperties(inputStream);
        return properties.getProperty("class");
    }

    private Class<? extends Module> getGuiceModule(ClassLoader classLoader) {
        InputStream inputStream = classLoader.getResourceAsStream("tomighty-plugin.properties");
        Properties properties = loadProperties(inputStream);
        //TODO: If there is no Guice Module defined, just ignore that
        String guiceModuleClassName = properties.getProperty("guice.module");

        try {
            //TODO: Check if this a Guice Module Class => ClassCastExceptions??
            return (Class<? extends Module>) classLoader.loadClass(guiceModuleClassName);
        } catch (ClassNotFoundException e) {
            //TODO: If there is no Guice Module defined, just ignore that
            throw new RuntimeException("Could not find Guice Binding Module");
        }


    }

    private Properties loadProperties(InputStream inputStream) {
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
