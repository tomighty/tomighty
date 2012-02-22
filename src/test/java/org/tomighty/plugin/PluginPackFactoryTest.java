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
