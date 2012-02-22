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
