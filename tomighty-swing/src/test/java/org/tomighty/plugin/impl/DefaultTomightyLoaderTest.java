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
import com.google.inject.Injector;
import org.junit.Test;
import org.tomighty.plugin.PluginLoader;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dobermai
 */
public class DefaultTomightyLoaderTest {


    @Test
    public void testReturnNullIfInstanceNotInInjectors() {
        PluginLoader pluginLoader = mock(PluginLoader.class);
        when(pluginLoader.getPluginInjectors()).thenReturn(new HashSet<Injector>() {{
            add(Guice.createInjector());
        }});
        DefaultTomightyLoader defaultTomightyLoader = new DefaultTomightyLoader(pluginLoader);
        Set instance = defaultTomightyLoader.getInstance(Set.class);

        assertNull(instance);
    }

    @Test
    public void testReturnInstance() {
        PluginLoader pluginLoader = mock(PluginLoader.class);
        when(pluginLoader.getPluginInjectors()).thenReturn(new HashSet<Injector>() {{
            add(Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bind(Set.class).to(HashSet.class);
                }
            }));
        }});
        DefaultTomightyLoader defaultTomightyLoader = new DefaultTomightyLoader(pluginLoader);
        Set instance = defaultTomightyLoader.getInstance(Set.class);

        assertNotNull(instance);
        assertThat(instance, instanceOf(HashSet.class));
    }
}
