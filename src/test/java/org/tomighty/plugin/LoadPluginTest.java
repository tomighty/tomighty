package org.tomighty.plugin;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.InjectedTest;
import org.tomighty.ioc.Binder;
import org.tomighty.ioc.Inject;
import org.tomighty.log.Log;
import org.tomighty.plugin.impl.DefaultPluginLoader;

import java.lang.reflect.Method;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoadPluginTest extends InjectedTest {

    private static final TestJars TEST_JARS = TestJars.HELLO_WORLD;

    private static final URL JAR1 = TEST_JARS.url("helloworld-plugin.jar");
    private static final URL JAR2 = TEST_JARS.url("slf4j-api-1.6.4.jar");
    private static final URL JAR3 = TEST_JARS.url("slf4j-simple-1.6.4.jar");

    private static final URL[] ALL_JARS = { JAR1, JAR2, JAR3 };

    @Inject
    private PluginLoader pluginLoader;

    private Plugin plugin;

    @Override
    protected void bind(Binder binder) {
        binder.bind(PluginLoader.class).to(DefaultPluginLoader.class);
    }

    @Before
    public void setUp() throws Exception {
        PluginPack pluginPack = mock(PluginPack.class);

        when(pluginPack.jars()).thenReturn(ALL_JARS);

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

}
