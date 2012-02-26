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

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class TestJars {

    public static final TestJars HELLO_WORLD = new TestJars("/plugins/helloworld");

    private final String directoryFromResource;

    public TestJars(String directoryFromResource) {
        this.directoryFromResource = directoryFromResource;
    }

    public File file(String jarName) {
        try {
            return new File(url(jarName).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URL url(String jarName) {
        String path = directoryFromResource + "/" + jarName;
        URL url = getClass().getResource(path);
        if(url == null)
            throw new IllegalArgumentException("Jar not found: " + path);
        return url;
    }

}
