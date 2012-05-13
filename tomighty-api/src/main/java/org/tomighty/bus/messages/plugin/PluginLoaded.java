package org.tomighty.bus.messages.plugin;

import org.tomighty.plugin.Plugin;

public class PluginLoaded {

    private final Plugin plugin;

    public PluginLoaded(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

}
