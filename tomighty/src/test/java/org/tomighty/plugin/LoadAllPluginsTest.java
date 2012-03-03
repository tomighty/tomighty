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
import org.junit.Test;
import org.tomighty.InjectedTest;
import org.tomighty.io.Directory;
import org.tomighty.plugin.impl.DefaultPluginManager;

import javax.inject.Inject;

import static java.util.Arrays.*;

import static org.mockito.Mockito.*;

public class LoadAllPluginsTest extends InjectedTest {

    @Inject
    private PluginManager pluginManager;

    @Inject
    private PluginLoader pluginLoader;

    @Inject
    private PluginPackFactory pluginPackFactory;

    @Override
    protected void bind(Binder binder) {
        binder.bind(PluginManager.class).to(DefaultPluginManager.class);
        binder.bind(PluginPackFactory.class).toInstance(mock(PluginPackFactory.class));
        binder.bind(PluginLoader.class).toInstance(mock(PluginLoader.class));
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
