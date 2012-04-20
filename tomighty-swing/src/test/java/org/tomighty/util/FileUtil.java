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

package org.tomighty.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtil {

    public static File createFile(String name, File directory) throws IOException {
        File file = new File(directory, name);
        file.createNewFile();
        return file;
    }

    public static File createSubdirectory(String name, File directory) {
        File subdir = new File(directory, name);
        subdir.mkdirs();
        return subdir;
    }

    public static File newTemporaryPath() throws IOException {
        File tempDir = File.createTempFile("tomighty-test-", null);
        tempDir.delete();
        return tempDir;
    }
    
    public static URL urlOf(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
