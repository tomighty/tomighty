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

package org.tomighty.plugin.impl;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginPack;
import org.tomighty.util.PluginPropertiesReader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DefaultPluginLoader implements PluginLoader {

    private final Injector injector;
    private final PluginPropertiesReader pluginPropertiesReader;

    private Set<Injector> injectors;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public DefaultPluginLoader(Injector injector, PluginPropertiesReader pluginPropertiesReader) {
        this.injector = injector;
        this.pluginPropertiesReader = pluginPropertiesReader;

        injectors = new HashSet<Injector>();
    }

    @PostConstruct
    public void postConstruct() {
        injectors.add(injector);
    }

    @Override
    public Plugin load(PluginPack pluginPack) {
        logger.info("Loading plugin {}", pluginPack);
        URLClassLoader classLoader = createClassLoader(pluginPack);
        Class<? extends Plugin> pluginClass = loadPluginClass(classLoader);

        Injector pluginInjector = createPluginInjector(classLoader);
        injectors.add(pluginInjector);
        return pluginInjector.getInstance(pluginClass);
    }

    @Override
    public Iterable<Injector> getPluginInjectors() {
        return Collections.unmodifiableSet(injectors);
    }

    private Injector createPluginInjector(final URLClassLoader classLoader) {
        Class<? extends Module> guiceModule = getGuiceModule(classLoader);
        try {
            return injector.createChildInjector(guiceModule.newInstance());
        } catch (InstantiationException e) {
            logger.error("Could not instantiate {}", guiceModule.getName());
        } catch (IllegalAccessException e) {
            logger.error("Could not instantiate {}", guiceModule.getName());
        }

        //If we cannot return a child injector, just use the standard parent injector for the plugin
        return injector;
    }

    private URLClassLoader createClassLoader(PluginPack pluginPack) {
        URL[] jars = pluginPack.jars();
        for (URL jar : jars)
            logger.info("Loading jar {}", jar);
        return new URLClassLoader(jars);
    }

    private Class<? extends Plugin> loadPluginClass(ClassLoader classLoader) {
        String pluginClassName = pluginPropertiesReader.getPluginClassName(classLoader);
        logger.info("Loading plugin class {}", pluginClassName);
        try {
            return (Class<? extends Plugin>) classLoader.loadClass(pluginClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private Class<? extends Module> getGuiceModule(ClassLoader classLoader) {

        String guiceModuleClassName = pluginPropertiesReader.getGuiceModuleClassName(classLoader);

        if (guiceModuleClassName != null) {

            try {
                return (Class<? extends Module>) classLoader.loadClass(guiceModuleClassName);
            } catch (ClassNotFoundException e) {
                logger.warn("Binding Module Class {} class is not found.!", guiceModuleClassName);
            } catch (ClassCastException cce) {
                logger.warn("Binding Module {} class is not a Guice Module!", guiceModuleClassName);
                logger.debug("Actual Exception:", cce);
            }

        } else {
            logger.warn("No Guice Binding Module defined in the tomighty-plugin.properties file.");
        }
        logger.info("Using default Guice Binding Module for the plugin");
        return DefaultModule.class;
    }

}
