package org.tomighty.plugin;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class DefaultPluginTest {

    private Plugin plugin;
    private Injector injector;

    @Before
    public void setUp() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Plugin.class).to(DefaultPlugin.class);
            }
        });

        plugin = injector.getInstance(Plugin.class);
    }

    //TODO test getPluginName
    //TODO test getPluginVersion

    @Test
    public void testHasNoMenuItem() {
        assertNull(plugin.getMenuItem());
    }

    @Test
    public void testInjectorIsTheSameUsedToCreateThePlugin() {
        assertSame(injector, plugin.getInjector());
    }

}
