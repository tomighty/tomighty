package org.tomighty.plugin;

/**
 * Creates an instance of a plugin.
 */
public interface PluginLoader {

    Plugin load(PluginPack pluginPack);

}
