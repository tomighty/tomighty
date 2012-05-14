package org.tomighty.plugin;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class DefaultPluginTest {

    //TODO test getPluginName
    //TODO test getPluginVersion
    //TODO test getMenuItem

    @Test
    public void testInjectorIsTheSameUsedToCreateThePlugin() {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Plugin.class).to(DefaultPlugin.class);
            }
        });

        Plugin plugin = injector.getInstance(Plugin.class);

        assertSame(injector, plugin.getInjector());
    }

}
