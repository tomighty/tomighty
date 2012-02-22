package org.tomighty.plugin;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class TestJars {

    public static final TestJars HELLO_WORLD = new TestJars("/plugins/helloworld");

    private final String directoryFromResource;

    public TestJars(String directoryFromResource) {
        this.directoryFromResource = directoryFromResource;
    }

    public File file(String jarName) {
        try {
            return new File(url(jarName).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URL url(String jarName) {
        String path = directoryFromResource + "/" + jarName;
        URL url = getClass().getResource(path);
        if(url == null)
            throw new IllegalArgumentException("Jar not found: " + path);
        return url;
    }

}
