/*
Copyright 2010 Célio Cidral Junior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.tomighty;

import java.awt.Component;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.bus.messages.TrayClick;
import org.tomighty.config.Options;
import org.tomighty.ioc.Container;
import org.tomighty.ioc.Factory;
import org.tomighty.ioc.Inject;
import org.tomighty.ioc.New;
import org.tomighty.log.Log;
import org.tomighty.ui.Tray;
import org.tomighty.ui.UiState;
import org.tomighty.ui.Window;
import org.tomighty.ui.states.InitialState;

public class Tomighty implements Runnable {
	
	@Inject private Window window;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Factory factory;
	@Inject @New private Log log;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Container container = new Container();
		Tomighty tomighty = container.get(Tomighty.class);
		Tray tray = container.get(Tray.class);
		SwingUtilities.invokeLater(tomighty);
		SwingUtilities.invokeLater(tray);
	}
	
	@Override
	public void run() {
		bus.subscribe(new SwitchState(), ChangeUiState.class);
		bus.subscribe(new ShowWindow(), TrayClick.class);
		render(InitialState.class);
	}
	
	private void render(Class<? extends UiState> stateClass) {
		UiState state = factory.create(stateClass);
		Component component;
		try {
			component = state.render();
		} catch (Exception error) {
			log.error("Failed to render state: "+state, error);
			return;
		}
		window.setComponent(component);
	}
	
	private class SwitchState implements Subscriber<ChangeUiState> {
		@Override
		public void receive(ChangeUiState message) {
			Class<? extends UiState> stateClass = message.getStateClass();
			render(stateClass);
			window.show(null);
		}
	}
	
	private class ShowWindow implements Subscriber<TrayClick> {
		@Override
		public void receive(TrayClick message) {
			if(options.autoHide() || !window.isVisible()) {
				window.show(message.mouseLocation());
			} else {
				window.setVisible(false);
			}
		}
	}
	
}
