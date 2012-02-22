package org.tomighty.io;

import org.tomighty.util.FileFilters;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileSystemDirectory implements Directory {

    private final File directory;

    public FileSystemDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public File path() {
        return directory;
    }

    @Override
    public void create() {
        directory.mkdirs();
    }

    @Override
    public List<Directory> subdirs() {
        ArrayList<Directory> list = new ArrayList<Directory>();
        File[] subdirs = directory.listFiles(FileFilters.directories());
        if(subdirs != null)
            for(File subdir : subdirs)
                list.add(new FileSystemDirectory(subdir));
        return list;
    }

    @Override
    public File[] filesByExtension(String extension) {
        FileFilter filter = FileFilters.withExtension(extension);
        File[] files = directory.listFiles(filter);
        return files != null ? files : new File[] { };
    }

}
