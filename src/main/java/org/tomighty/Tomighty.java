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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.bus.messages.TrayClick;
import org.tomighty.ui.Tray;
import org.tomighty.ui.UiState;
import org.tomighty.ui.Window;
import org.tomighty.ui.states.InitialState;
import org.tomighty.util.New;

public class Tomighty {
	
	private Window window;
	private Logger logger;
	private Tray tray;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Tomighty().start();
	}

	public Tomighty() {
		logger = Logger.getLogger(getClass().getName());
		window = new Window();
		tray = new Tray();
	}
	
	public void start() {
		Bus.subscribe(new SwitchState(), ChangeUiState.class);
		Bus.subscribe(new ShowWindow(), TrayClick.class);
		render(InitialState.class);
		tray.start();
	}
	
	private void render(Class<? extends UiState> stateClass) {
		UiState state = New.instanceOf(stateClass);
		Component component;
		try {
			component = state.render();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to render state: "+state, e);
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
			window.show(message.mouseLocation());
		}
	}
	
}
