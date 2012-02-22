package org.tomighty.plugin.impl;

import org.tomighty.io.Directory;
import org.tomighty.plugin.PluginPack;
import org.tomighty.plugin.PluginPackFactory;

public class DefaultPluginPackFactory implements PluginPackFactory {

    @Override
    public PluginPack createFrom(Directory directory) {
        return new DirectoryPluginPack(directory);
    }

}
