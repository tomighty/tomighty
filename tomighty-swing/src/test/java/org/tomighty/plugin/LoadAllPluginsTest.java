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

import org.junit.Before;
import org.junit.Test;
import org.tomighty.io.Directory;
import org.tomighty.plugin.impl.DefaultPluginManager;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class LoadAllPluginsTest {

    private PluginManager pluginManager;
    private PluginLoader pluginLoader;
    private PluginPackFactory pluginPackFactory;

    @Before
    public void setUp() {
        pluginPackFactory = mock(PluginPackFactory.class);
        pluginLoader = mock(PluginLoader.class);
        pluginManager = new DefaultPluginManager(pluginLoader, pluginPackFactory);
    }

    @Test
    public void loadTwoPlugins() throws Exception {
        PluginPack foo = mock(PluginPack.class);
        PluginPack bar = mock(PluginPack.class);

        Directory directory = mock(Directory.class);
        Directory fooDirectory = mock(Directory.class);
        Directory barDirectory = mock(Directory.class);

        when(directory.subdirs()).thenReturn(asList(fooDirectory, barDirectory));
        when(pluginPackFactory.createFrom(fooDirectory)).thenReturn(foo);
        when(pluginPackFactory.createFrom(barDirectory)).thenReturn(bar);
        
        pluginManager.loadPluginsFrom(directory);

        verify(pluginLoader).load(foo);
        verify(pluginLoader).load(bar);
    }

}
