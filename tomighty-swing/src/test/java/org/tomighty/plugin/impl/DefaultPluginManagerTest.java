/*
 * Copyright (c) 2010-2012 Célio Cidral Junior, Dominik Obermaier.
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */

package org.tomighty.plugin.impl;

import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;
import org.tomighty.bus.Bus;
import org.tomighty.io.Directory;
import org.tomighty.plugin.*;

import java.awt.*;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author dobermai
 */
public class DefaultPluginManagerTest {

    private DefaultPluginManager defaultPluginManager;

    private class PluginStub implements Plugin {

        @Override
        public String getPluginName() {
            return "stub";
        }

        @Override
        public PluginVersion getPluginVersion() {
            return new PluginVersion(1, 0, 0);
        }

        @Override
        public MenuItem getMenuItem() {
            return null;
        }

        @Override
        public Injector getInjector() {
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        //Initialize Stubs so we can test the relevant methods without getting NPEs ;)
        PluginPackFactory pluginPackFactoryMock = mock(PluginPackFactory.class);

        Directory pluginsDirectory = mock(Directory.class);
        Directory somePluginSubdirectory = mock(Directory.class);
        when(pluginsDirectory.subdirs()).thenReturn(asList(somePluginSubdirectory));

        PluginLoader loaderMock = mock(PluginLoader.class);
        when(loaderMock.load(any(PluginPack.class))).thenReturn(new PluginStub());

        defaultPluginManager = new DefaultPluginManager(loaderMock, pluginPackFactoryMock, mock(Bus.class));
        defaultPluginManager.loadPluginsFrom(pluginsDirectory);
    }

    @Test
    public void TestGetLoadedPluginsWhenPluginsAreLoaded() {

        Set<Plugin> loadedPlugins = defaultPluginManager.getLoadedPlugins();

        assertEquals(1, loadedPlugins.size());
    }


    @Test
    public void testDisablePluginWithNullParameter() {

        boolean disabled = defaultPluginManager.disablePlugin(null);
        assertFalse(disabled);

        Set<Plugin> loadedPlugins = defaultPluginManager.getLoadedPlugins();

        assertEquals(1, loadedPlugins.size());
    }

    @Test
    public void testDisablePlugin() {

        boolean disabled = defaultPluginManager.disablePlugin("stub");
        assertTrue(disabled);

        Set<Plugin> loadedPlugins = defaultPluginManager.getLoadedPlugins();

        assertEquals(0, loadedPlugins.size());
    }

    @Test
    public void testDisableNonExistantPlugin() {

        boolean disabled = defaultPluginManager.disablePlugin("nonexistant");
        assertFalse(disabled);

        Set<Plugin> loadedPlugins = defaultPluginManager.getLoadedPlugins();

        assertEquals(1, loadedPlugins.size());
    }
}
