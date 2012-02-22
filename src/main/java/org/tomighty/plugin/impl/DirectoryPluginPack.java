package org.tomighty.plugin.impl;

import org.tomighty.io.Directory;
import org.tomighty.plugin.PluginPack;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DirectoryPluginPack implements PluginPack {

    private final Directory directory;

    public DirectoryPluginPack(Directory directory) {
        this.directory = directory;
    }

    @Override
    public URL[] jars() {
        File[] files = directory.filesByExtension("jar");
        URL[] urls = new URL[files.length];
        for(int index = 0; index < files.length; index++)
            urls[index] = urlFor(files[index]);
        return urls;
    }

    private URL urlFor(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
