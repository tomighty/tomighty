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

package org.tomighty.plugin;

import com.google.inject.Binder;
import org.junit.Before;
import org.junit.Test;
import org.tomighty.InjectedTest;
import org.tomighty.bus.Bus;
import org.tomighty.plugin.impl.DefaultPluginLoader;

import javax.inject.Inject;
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
    private static final URL JAR4 = TEST_JARS.url("commons-math-2.2.jar");

    private static final URL[] ALL_JARS = { JAR1, JAR2, JAR3, JAR4 };

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
        classLoader.loadClass("org.apache.commons.math.random.RandomGenerator");
    }

    @Test
    public void dependenciesAnnotatedWithInjectAreInjected() throws Exception {
        Method getter = plugin.getClass().getMethod("getInjectedBus");
        Object injectedObject = getter.invoke(plugin);
        assertEquals(Bus.class, injectedObject.getClass());
    }

}
