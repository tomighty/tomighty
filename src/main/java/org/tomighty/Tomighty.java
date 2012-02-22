/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.ChangeUiState;
import org.tomighty.bus.messages.ui.TrayClick;
import org.tomighty.bus.messages.ui.UiStateChanged;
import org.tomighty.config.Directories;
import org.tomighty.config.Options;
import org.tomighty.ioc.*;
import org.tomighty.ioc.Container;
import org.tomighty.log.Log;
import org.tomighty.plugin.PluginLoader;
import org.tomighty.plugin.PluginManager;
import org.tomighty.plugin.PluginPackFactory;
import org.tomighty.plugin.impl.DefaultPluginLoader;
import org.tomighty.plugin.impl.DefaultPluginManager;
import org.tomighty.plugin.impl.DefaultPluginPackFactory;
import org.tomighty.ui.Tray;
import org.tomighty.ui.TrayManager;
import org.tomighty.ui.UiState;
import org.tomighty.ui.Window;
import org.tomighty.ui.state.InitialState;
import org.tomighty.ui.tray.AwtTray;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

public class Tomighty implements Initializable, Runnable {
	
	@Inject private Window window;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Factory factory;
    @Inject private PluginManager pluginManager;
    @Inject private Directories directories;
    @Inject @New private Log log;
    private UiState currentState;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Container container = createContainer();
		Tomighty tomighty = container.get(Tomighty.class);
		TrayManager trayManager = container.get(TrayManager.class);
		invokeLater(tomighty);
		invokeLater(trayManager);
	}

	private static Container createContainer() {
		Container container = new Container();
		Binder binder = container.binder();
		binder.bind(Tray.class).to(AwtTray.class);
        binder.bind(PluginManager.class).to(DefaultPluginManager.class);
        binder.bind(PluginLoader.class).to(DefaultPluginLoader.class);
        binder.bind(PluginPackFactory.class).to(DefaultPluginPackFactory.class);
		return container;
	}
	
	@Override
	public void initialize() {
		bus.subscribe(new SwitchState(), ChangeUiState.class);
		bus.subscribe(new ShowWindow(), TrayClick.class);
	}
	
	@Override
	public void run() {
		render(InitialState.class);
        pluginManager.loadPluginsFrom(directories.plugins());
	}

    private void render(Class<? extends UiState> stateClass) {
		if(currentState != null) {
			currentState.beforeDetaching();
		}
		currentState = factory.create(stateClass);
		Component component;
		try {
			component = currentState.render();
		} catch (Exception error) {
			log.error("Failed to render state: "+currentState, error);
			return;
		}
		window.setComponent(component);
		currentState.afterRendering();
	}
	
	private class SwitchState implements Subscriber<ChangeUiState> {
		@Override
		public void receive(final ChangeUiState message) {
			invokeLater(new Runnable() {
				@Override
				public void run() {
					Class<? extends UiState> stateClass = message.getStateClass();
					render(stateClass);
					window.show(null);
					bus.publish(new UiStateChanged(currentState));
				}
			});
		}
	}
	
	private class ShowWindow implements Subscriber<TrayClick> {
		@Override
		public void receive(final TrayClick message) {
			invokeLater(new Runnable() {
				@Override
				public void run() {
					if(options.ui().autoHideWindow() || !window.isVisible()) {
						window.show(message.mouseLocation());
					} else {
						window.setVisible(false);
					}
				}
			});
		}
	}
	
}
