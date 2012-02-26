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
