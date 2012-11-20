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

package org.tomighty.plugin;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.bus.messages.plugin.PluginLoaded;
import org.tomighty.io.Directory;
import org.tomighty.mock.bus.MockBus;
import org.tomighty.plugin.impl.DefaultPluginManager;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class LoadAllPluginsFromDirectoryTest {

    private PluginManager pluginManager;
    private Directory directory;
    private Plugin plugin1;
    private Plugin plugin2;
    private MockBus bus;

    @Before
    public void setUp() {
        directory = mock(Directory.class);
        plugin1 = mock(Plugin.class);
        plugin2 = mock(Plugin.class);

        Directory pluginDirectory1 = mock(Directory.class);
        Directory pluginDirectory2 = mock(Directory.class);
        when(directory.subdirs()).thenReturn(asList(pluginDirectory1, pluginDirectory2));

        PluginPackFactory pluginPackFactory = mock(PluginPackFactory.class);
        PluginPack pluginPack1 = mock(PluginPack.class);
        PluginPack pluginPack2 = mock(PluginPack.class);
        when(pluginPackFactory.createFrom(pluginDirectory1)).thenReturn(pluginPack1);
        when(pluginPackFactory.createFrom(pluginDirectory2)).thenReturn(pluginPack2);

        PluginLoader pluginLoader = mock(PluginLoader.class);
        when(pluginLoader.load(pluginPack1)).thenReturn(plugin1);
        when(pluginLoader.load(pluginPack2)).thenReturn(plugin2);

        bus = new MockBus();
        pluginManager = new DefaultPluginManager(pluginLoader, pluginPackFactory, bus);
    }

    @Test
    public void loadTwoPlugins() throws Exception {
        pluginManager.loadPluginsFrom(directory);

        List<Object> publishedMessages = bus.getPublishedMessages();

        assertEquals("Two messages were published, one for each loaded plugin", 2, publishedMessages.size());

        PluginLoaded pluginLoaded1 = (PluginLoaded) publishedMessages.get(0);
        PluginLoaded pluginLoaded2 = (PluginLoaded) publishedMessages.get(1);
        assertSame("First message contains plugin #1", plugin1, pluginLoaded1.getPlugin());
        assertSame("Second message contains plugin #2", plugin2, pluginLoaded2.getPlugin());
    }

    @Test
    public void testGetPlugins() {
        pluginManager.loadPluginsFrom(directory);

        List<Plugin> plugins = pluginManager.getPlugins();
        assertEquals(2, plugins.size());
        assertTrue(plugins.contains(plugin1));
        assertTrue(plugins.contains(plugin2));
    }

}
