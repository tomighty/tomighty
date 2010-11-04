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

import java.awt.Component;

import static javax.swing.SwingUtilities.*;

import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.bus.messages.TrayClick;
import org.tomighty.bus.messages.UiStateChanged;
import org.tomighty.config.Options;
import org.tomighty.ioc.Container;
import org.tomighty.ioc.Factory;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;
import org.tomighty.ui.Tray;
import org.tomighty.ui.UiState;
import org.tomighty.ui.Window;
import org.tomighty.ui.state.InitialState;

public class Tomighty implements Initializable, Runnable {
	
	@Inject private Window window;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Factory factory;
	@Inject @New private Log log;
	private UiState currentState;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Container container = new Container();
		Tomighty tomighty = container.get(Tomighty.class);
		Tray tray = container.get(Tray.class);
		invokeLater(tomighty);
		invokeLater(tray);
	}
	
	@Override
	public void initialize() {
		bus.subscribe(new SwitchState(), ChangeUiState.class);
		bus.subscribe(new ShowWindow(), TrayClick.class);
	}
	
	@Override
	public void run() {
		render(InitialState.class);
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
