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

package org.tomighty.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.tomighty.util.FileUtil.*;

public class NonexistentDirectoryTest {

    private Directory directory;
    private File path;

    @Before
    public void setUp() throws IOException {
        path = newTemporaryPath();
        directory = new FileSystemDirectory(path);
    }

    @Test
    public void subdirsReturnsEmptyList() {
        List<Directory> subdirs = directory.subdirs();
        assertNotNull("List should not be null", subdirs);
        assertEquals("List should be empty", 0, subdirs.size());
    }

    @Test
    public void filesByExtensionReturnsEmptyList() {
        File[] files = directory.filesByExtension("txt");
        assertNotNull("Array should not be null, files", files);
        assertEquals("Array should be empty", 0, files.length);
    }

    @Test
    public void createDirectory() {
        directory.create();
        assertTrue("Directory should exist", path.exists());
    }

}
