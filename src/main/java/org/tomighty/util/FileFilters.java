package org.tomighty.util;

import java.io.File;
import java.io.FileFilter;

public class FileFilters {

    public static FileFilter withExtension(final String extension) {
        return new FileFilter() {
            @Override
            public boolean accept(File path) {
                return path.isFile() && path.getName().endsWith("." + extension);
            }
        };
    }

    public static FileFilter directories() {
        return new FileFilter() {
            @Override
            public boolean accept(File path) {
                return path.isDirectory();
            }
        };
    }

}
