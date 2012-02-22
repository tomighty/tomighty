package org.tomighty.plugin.impl;

import org.tomighty.io.Directory;
import org.tomighty.ioc.Inject;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.PluginPack;
import org.tomighty.plugin.PluginPackFactory;

public class DefaultPluginManager implements PluginManager {
    
    @Inject
    private PluginLoader pluginLoader;
    
    @Inject
    private PluginPackFactory pluginPackFactory;

    @Override
    public void loadPluginsFrom(Directory directory) {
        for(Directory subdirectory : directory.subdirs()) {
            PluginPack pluginPack = pluginPackFactory.createFrom(subdirectory);
            pluginLoader.load(pluginPack);
        }
    }

}
