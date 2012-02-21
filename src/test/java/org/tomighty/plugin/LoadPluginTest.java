package org.tomighty.plugin;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.InjectedTest;
import org.tomighty.ioc.Binder;
import org.tomighty.ioc.Inject;
import org.tomighty.log.Log;
import org.tomighty.plugin.impl.DefaultPluginLoader;
import org.tomighty.plugin.impl.PluginPackDirectory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class LoadPluginTest extends InjectedTest {

    @Inject
    private PluginLoader pluginLoader;

    private Plugin plugin;

    @Override
    protected void bind(Binder binder) {
        binder.bind(PluginLoader.class).to(DefaultPluginLoader.class);
    }

    @Before
    public void setUp() throws Exception {
        File pluginDirectory = directoryFromResource("/plugins/helloworld");
        PluginPack pluginPack = new PluginPackDirectory(pluginDirectory);

        plugin = pluginLoader.load(pluginPack);
    }

    @Test
    public void pluginIsLoaded() throws Exception {
        assertNotNull(plugin);
        assertEquals("org.tomighty.plugin.helloworld.HelloWorldPlugin", plugin.getClass().getName());
    }

    @Test
    public void pluginIsLoadedOnItsOwnClassLoader() throws Exception {
        assertNotSame(getClass().getClassLoader(), plugin.getClass().getClassLoader());
    }

    @Test
    public void dependencyJarsAreLoadedInThePluginClassLoader() throws ClassNotFoundException {
        ClassLoader classLoader = plugin.getClass().getClassLoader();
        classLoader.loadClass("org.slf4j.Logger");
        classLoader.loadClass("org.slf4j.impl.SimpleLogger");
    }

    @Test
    public void dependenciesAnnotatedWithInjectAreInjected() throws Exception {
        Method getInjectedLogger = plugin.getClass().getMethod("getInjectedLogger");
        Object injectedLogger = getInjectedLogger.invoke(plugin);
        assertEquals(Log.class, injectedLogger.getClass());
    }

    private File directoryFromResource(String path) throws URISyntaxException {
        URL url = getClass().getResource(path);
        return new File(url.toURI());
    }

}
