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
