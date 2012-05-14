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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.TomightyLoader;

import javax.inject.Inject;

/**
 * @author dobermai
 */
public class DefaultTomightyLoader implements TomightyLoader {

    private final PluginManager pluginManager;

    private static final Logger log = LoggerFactory.getLogger(DefaultTomightyLoader.class);

    @Inject
    public DefaultTomightyLoader(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public <T> T getInstance(final Class<T> clazz) {
        Iterable<Plugin> plugins = pluginManager.getLoadedPlugins();
        for (Plugin plugin : plugins) {
            Injector pluginInjector = plugin.getInjector();
            try {
                //TODO: Add caching for often used instances. And what to do if something is bound in two injectors?
                T instance = pluginInjector.getInstance(clazz);
                return instance;
            } catch (Exception e) {
                log.trace("Did not find instance for {} in Plugin Injector for {}", clazz, pluginInjector);
            }
        }
        log.debug("No Instance found for {}, returning null", clazz);
        return null;
    }

}
