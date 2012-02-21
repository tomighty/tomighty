package org.tomighty.util;

import java.io.File;
import java.io.FileFilter;

public class FileFilters {

    public static FileFilter withExtension(final String extension) {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("." + extension);
            }
        };
    }

}
