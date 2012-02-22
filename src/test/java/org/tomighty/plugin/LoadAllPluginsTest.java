package org.tomighty.plugin;

import org.junit.Test;
import org.tomighty.InjectedTest;
import org.tomighty.io.Directory;
import org.tomighty.ioc.Binder;
import org.tomighty.ioc.Inject;
import org.tomighty.plugin.impl.DefaultPluginManager;

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
        binder.bind(PluginPackFactory.class).to(mock(PluginPackFactory.class));
        binder.bind(PluginLoader.class).to(mock(PluginLoader.class));
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
