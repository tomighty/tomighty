package org.tomighty.plugin;

import java.net.URL;

/**
 * A plugin's jar files.
 *
 * <p>
 *     A plugin pack consists of a jar file that contains the
 *     plugin plus all other jars that the plugin depends upon.
 * </p>
 */
public interface PluginPack {

    URL[] jars();

}
