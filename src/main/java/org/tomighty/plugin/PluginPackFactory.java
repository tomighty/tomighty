package org.tomighty.plugin;

import org.tomighty.io.Directory;

public interface PluginPackFactory {

    PluginPack createFrom(Directory directory);

}
