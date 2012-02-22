package org.tomighty.io;

import java.io.File;
import java.util.List;

public interface Directory {

    File path();

    void create();

    List<Directory> subdirs();

    File[] filesByExtension(String extension);

}
