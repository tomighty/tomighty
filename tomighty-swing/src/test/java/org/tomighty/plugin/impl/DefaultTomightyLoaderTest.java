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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.tomighty.plugin.Plugin;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.TomightyLoader;

import java.util.*;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dobermai
 */
public class DefaultTomightyLoaderTest {

    private TomightyLoader tomightyLoader;
    private Plugin pluginWithList;
    private Plugin pluginWithMap;

    @Before
    public void setUp() {
        pluginWithList = mock(Plugin.class);
        pluginWithMap = mock(Plugin.class);

        when(pluginWithList.getInjector()).thenReturn(Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(List.class).to(ArrayList.class);
            }
        }));

        when(pluginWithMap.getInjector()).thenReturn(Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Map.class).to(HashMap.class);
            }
        }));

        PluginManager pluginManager = mock(PluginManager.class);
        when(pluginManager.getLoadedPlugins()).thenReturn(setOfPlugins(pluginWithList, pluginWithMap));

        tomightyLoader = new DefaultTomightyLoader(pluginManager);
    }

    @Test
    public void testReturnNullIfInstanceNotInInjectors() {
        Queue queue = tomightyLoader.getInstance(Queue.class);
        assertNull(queue);
    }

    @Test
    public void testGetInstanceOfListFromPluginWithList() {
        List list = tomightyLoader.getInstance(List.class);
        assertThat(list, instanceOf(ArrayList.class));
    }

    @Test
    public void testGetInstanceOfMapFromPluginWithMap() {
        Map map = tomightyLoader.getInstance(Map.class);
        assertThat(map, instanceOf(HashMap.class));
    }

    private Set<Plugin> setOfPlugins(Plugin... plugins) {
        Set<Plugin> set = new HashSet<Plugin>();
        for(Plugin plugin : plugins)
            set.add(plugin);
        return set;
    }

}
