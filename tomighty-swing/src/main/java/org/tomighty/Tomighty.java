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

package org.tomighty;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycila.inject.jsr250.Jsr250;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.ChangeUiState;
import org.tomighty.bus.messages.ui.TrayClick;
import org.tomighty.bus.messages.ui.UiStateChanged;
import org.tomighty.config.Directories;
import org.tomighty.config.Options;
import org.tomighty.inject.TomightyModule;
import org.tomighty.plugin.PluginManager;
import org.tomighty.ui.tray.TrayManager;
import org.tomighty.ui.UiState;
import org.tomighty.ui.Window;
import org.tomighty.ui.state.InitialState;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

public class Tomighty implements Runnable {

	@Inject private Window window;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Injector injector;
    @Inject private PluginManager pluginManager;
    @Inject private Directories directories;
    private UiState currentState;
    private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Injector injector = Guice.createInjector(new TomightyModule(), Jsr250.newJsr250Module());

		Tomighty tomighty = injector.getInstance(Tomighty.class);
		invokeLater(tomighty);
		TrayManager trayManager = injector.getInstance(TrayManager.class);
		invokeLater(trayManager);
	}

	@PostConstruct
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
		currentState = injector.getInstance(stateClass);
		Component component;
		try {
			component = currentState.render();
		} catch (Exception error) {
            logger.error("Failed to render state: " + currentState, error);
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
