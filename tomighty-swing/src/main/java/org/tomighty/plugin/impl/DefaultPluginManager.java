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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomighty.io.Directory;
import org.tomighty.plugin.*;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

public class DefaultPluginManager implements PluginManager {

    private static Logger log = LoggerFactory.getLogger(DefaultPluginManager.class);

    private PluginLoader pluginLoader;
    private PluginPackFactory pluginPackFactory;

    private Set<Plugin> loadedPlugins;

    @Inject
    public DefaultPluginManager(PluginLoader pluginLoader, PluginPackFactory pluginPackFactory) {
        this.pluginLoader = pluginLoader;
        this.pluginPackFactory = pluginPackFactory;

        loadedPlugins = new HashSet<Plugin>();
    }

    @Override
    public void loadPluginsFrom(Directory directory) {
        for (Directory subdirectory : directory.subdirs()) {
            PluginPack pluginPack = pluginPackFactory.createFrom(subdirectory);

            Plugin loadedPlugin = pluginLoader.load(pluginPack);

            loadedPlugins.add(loadedPlugin);
            log.info("Loaded {} with Version {}", loadedPlugin.getPluginName(), loadedPlugin.getPluginVersion());
        }
    }

    @Override
    public Set<Plugin> getLoadedPlugins() {
        return unmodifiableSet(loadedPlugins);
    }

}
