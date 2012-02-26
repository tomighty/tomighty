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

package org.tomighty.plugin.impl;

import org.tomighty.io.Directory;
import org.tomighty.plugin.PluginPack;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DirectoryPluginPack implements PluginPack {

    private final Directory directory;

    public DirectoryPluginPack(Directory directory) {
        this.directory = directory;
    }

    @Override
    public URL[] jars() {
        File[] files = directory.filesByExtension("jar");
        URL[] urls = new URL[files.length];
        for(int index = 0; index < files.length; index++)
            urls[index] = urlFor(files[index]);
        return urls;
    }

    private URL urlFor(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return directory.path().toString();
    }

}
