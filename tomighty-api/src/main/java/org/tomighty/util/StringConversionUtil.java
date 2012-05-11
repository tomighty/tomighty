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

package org.tomighty.util;

import org.tomighty.plugin.PluginVersion;

/**
 * @author dobermai
 */
public class StringConversionUtil {

    public static PluginVersion convertStringToPluginVersion(String versionString) {

        if (versionString == null) {
            throw new RuntimeException("Cannot convert null to a PluginVersion Instance");
        }

        String[] splitted = versionString.split("\\.");

        if (splitted.length != 3) {
            throw new RuntimeException("Version number does not match style 'major.minor.bugfix'");
        }

        Integer.valueOf(splitted[0]);

        return new PluginVersion(Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]), Integer.valueOf(splitted[2]));
    }
}
