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

import org.junit.Test;
import org.tomighty.io.Directory;
import org.tomighty.plugin.impl.DefaultPluginPackFactory;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class PluginPackFactoryTest {

    @Test
    public void createDoesNotReturnNull() {
        PluginPackFactory factory = new DefaultPluginPackFactory();
        Directory directory = mock(Directory.class);
        PluginPack pluginPack = factory.createFrom(directory);

        assertNotNull(pluginPack);
    }

}
