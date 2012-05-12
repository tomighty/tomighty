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

package org.tomighty.plugin;

import org.tomighty.util.PluginPropertiesReader;
import org.tomighty.util.StringConversionUtil;

import javax.inject.Inject;
import java.awt.*;

/**
 * Default Implementation for Plugins which reads the relevant data out of the tomighty-plugin.properties file.
 *
 * @author dobermai
 */
public class DefaultPlugin implements Plugin {


    @Inject
    private PluginPropertiesReader pluginPropertiesReader;

    @Override
    public String getPluginName() {
        String pluginName = pluginPropertiesReader.getPluginName(getClass().getClassLoader());
        if (pluginName == null) {
            throw new RuntimeException("No entry in the tomighty-plugin.properties file found for the plugin name!");
        }
        return pluginName;
    }

    @Override
    public PluginVersion getPluginVersion() {
        String pluginVersion = pluginPropertiesReader.getPluginVersion(getClass().getClassLoader());
        return StringConversionUtil.convertStringToPluginVersion(pluginVersion);
    }

    @Override
    public MenuItem getMenuItem() {
        //Don't use a Menu Item by default
        return null;
    }
}
