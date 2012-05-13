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

package org.tomighty.plugin;

import org.tomighty.io.Directory;

import java.util.Set;

public interface PluginManager {

    void loadPluginsFrom(Directory directory);

    Set<Plugin> getLoadedPlugins();

    /**
     * Disables a Plugin with a given name. If the plugin was disabled sucessfully,
     * the method will return <code>true</code>. If the plugin could not be disabled,
     * e.g. when the plugi was not enabled or the pluginName was <code>null</code>, <code>false</code>
     * will be returned
     *
     * @param pluginName the name of the plugin
     * @return <code>true</code> if the plugin was disabled sucessfully, <code>false</code> otherwise
     */
    boolean disablePlugin(String pluginName);
}
