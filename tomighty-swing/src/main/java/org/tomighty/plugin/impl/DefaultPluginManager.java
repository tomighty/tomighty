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

import org.tomighty.io.Directory;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.PluginPack;
import org.tomighty.plugin.PluginPackFactory;

import javax.inject.Inject;

public class DefaultPluginManager implements PluginManager {
    
    private PluginLoader pluginLoader;
    private PluginPackFactory pluginPackFactory;

    @Inject
    public DefaultPluginManager(PluginLoader pluginLoader, PluginPackFactory pluginPackFactory) {
        this.pluginLoader = pluginLoader;
        this.pluginPackFactory = pluginPackFactory;
    }

    @Override
    public void loadPluginsFrom(Directory directory) {
        for(Directory subdirectory : directory.subdirs()) {
            PluginPack pluginPack = pluginPackFactory.createFrom(subdirectory);
            pluginLoader.load(pluginPack);
        }
    }

}
