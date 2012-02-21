package org.tomighty.plugin.impl;

import org.tomighty.plugin.PluginPack;
import org.tomighty.util.FileFilters;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginPackDirectory implements PluginPack {

    private final File directory;

    public PluginPackDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public URL[] getJars() {
        List<URL> jars = jarsFrom(directory);
        return jars.toArray(new URL[jars.size()]);
    }

    private List<URL> jarsFrom(File directory) {
        List<URL> list = new ArrayList<URL>();
        FileFilter jarsOnly = FileFilters.withExtension("jar");

        for(File file : directory.listFiles(jarsOnly))
            list.add(urlFor(file));

        return list;
    }

    private URL urlFor(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
