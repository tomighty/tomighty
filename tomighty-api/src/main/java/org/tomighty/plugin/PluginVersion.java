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

/**
 * @author dobermai
 */
public class PluginVersion {

    private final int major;

    private final int minor;

    private final int bugfix;

    public PluginVersion(final int major, final int minor, final int bugfix) {
        this.major = major;
        this.minor = minor;
        this.bugfix = bugfix;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getBugfix() {
        return bugfix;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PluginVersion that = (PluginVersion) o;

        if (bugfix != that.bugfix) return false;
        if (major != that.major) return false;
        if (minor != that.minor) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + bugfix;
        return result;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + bugfix;
    }
}
