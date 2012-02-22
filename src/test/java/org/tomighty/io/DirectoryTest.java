package org.tomighty.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.tomighty.util.FileUtil.*;

public class DirectoryTest {

    private Directory directory;

    private File directoryPath;
    private File subdirPath1;
    private File subdirPath2;
    private File subdirPath3;
    private File textFile1;
    private File textFile2;

    @Before
    public void setUp() throws IOException {
        directoryPath = newTemporaryPath();
        subdirPath1 = createSubdirectory("subdir1", directoryPath);
        subdirPath2 = createSubdirectory("subdir2", directoryPath);
        subdirPath3 = createSubdirectory("subdir3.txt", directoryPath);
        textFile1 = createFile("textFile1.txt", directoryPath);
        textFile2 = createFile("textFile2.txt", directoryPath);
        createFile("zipfile.zip", directoryPath);

        directoryPath.mkdirs();

        directory = new FileSystemDirectory(directoryPath);
    }

    @Test
    public void path() {
        assertEquals(directoryPath, directory.path());
    }

    @Test
    public void subdirs() {
        List<Directory> subdirs = directory.subdirs();

        assertEquals(3, subdirs.size());

        Directory subdir1 = subdirs.get(0);
        Directory subdir2 = subdirs.get(1);
        Directory subdir3 = subdirs.get(2);

        assertEquals(subdirPath1, subdir1.path());
        assertEquals(subdirPath2, subdir2.path());
        assertEquals(subdirPath3, subdir3.path());
    }

    @Test
    public void filesByExtension() {
        File[] files = directory.filesByExtension("txt");
        
        assertEquals(2, files.length);
        assertEquals(textFile1, files[0]);
        assertEquals(textFile2, files[1]);
    }

}
