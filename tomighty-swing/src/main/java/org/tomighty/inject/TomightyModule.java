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

package org.tomighty.inject;

import com.google.inject.AbstractModule;
import org.tomighty.bus.Bus;
import org.tomighty.bus.DefaultBus;
import org.tomighty.config.Configuration;
import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.PluginPackFactory;
import org.tomighty.plugin.TomightyLoader;
import org.tomighty.plugin.impl.DefaultPluginLoader;
import org.tomighty.plugin.impl.DefaultPluginManager;
import org.tomighty.plugin.impl.DefaultPluginPackFactory;
import org.tomighty.plugin.impl.DefaultTomightyLoader;
import org.tomighty.resources.cache.Caches;
import org.tomighty.sound.SoundPlayer;
import org.tomighty.sound.Sounds;
import org.tomighty.time.DefaultTimer;
import org.tomighty.time.Timer;
import org.tomighty.ui.Tray;
import org.tomighty.ui.TrayManager;
import org.tomighty.ui.Window;
import org.tomighty.ui.swing.gauge.Gauge;
import org.tomighty.ui.theme.Look;
import org.tomighty.ui.tray.AwtTray;
import org.tomighty.ui.tray.PluginsMenu;
import org.tomighty.ui.tray.TrayMenu;
import org.tomighty.ui.tray.menu.DefaultPluginsMenu;
import org.tomighty.ui.tray.menu.DefaultTrayMenu;

import static com.google.inject.Scopes.SINGLETON;

public class TomightyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Bus.class).to(DefaultBus.class).in(SINGLETON);
        bind(Timer.class).to(DefaultTimer.class).in(SINGLETON);

        bind(Tray.class).to(AwtTray.class).in(SINGLETON);
        bind(TrayManager.class).in(SINGLETON);
        bind(TrayMenu.class).to(DefaultTrayMenu.class).in(SINGLETON);
        bind(PluginsMenu.class).to(DefaultPluginsMenu.class).in(SINGLETON);
        bind(Window.class).in(SINGLETON);
        bind(Gauge.class).in(SINGLETON);
        bind(Options.class).in(SINGLETON);
        bind(Configuration.class).in(SINGLETON);
        bind(Messages.class).in(SINGLETON);
        bind(Sounds.class).in(SINGLETON);
        bind(SoundPlayer.class).in(SINGLETON);
        bind(Caches.class).in(SINGLETON);
        bind(Look.class).in(SINGLETON);

        bind(PluginManager.class).to(DefaultPluginManager.class).in(SINGLETON);
        bind(PluginLoader.class).to(DefaultPluginLoader.class).in(SINGLETON);
        bind(PluginPackFactory.class).to(DefaultPluginPackFactory.class).in(SINGLETON);
        bind(TomightyLoader.class).to(DefaultTomightyLoader.class).in(SINGLETON);
    }

}
