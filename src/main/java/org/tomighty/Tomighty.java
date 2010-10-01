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
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.bus.messages.TrayClick;
import org.tomighty.states.InitialState;
import org.tomighty.ui.Tray;
import org.tomighty.ui.Window;
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
		Bus.subscribe(new SwitchState(), ChangeState.class);
		Bus.subscribe(new ShowWindow(), TrayClick.class);
		render(InitialState.class);
		tray.start();
	}
	
	private void render(Class<? extends State> stateClass) {
		State state = New.instanceOf(stateClass);
		Component component;
		try {
			component = state.render();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to render state: "+state, e);
			return;
		}
		window.setComponent(component);
	}
	
	private void showWindow() {
		showWindow(null);
	}
	
	private void showWindow(Point mouseLocation) {
		if(mouseLocation != null)
		{
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();	
			Rectangle bounds = env.getMaximumWindowBounds();
			int x = mouseLocation.x - window.getWidth() / 2;
			int y = bounds.height - window.getHeight() - 5;
			window.setLocation(x, y);
		}
		window.setVisible(true);
	}

	private class SwitchState implements Subscriber<ChangeState> {
		@Override
		public void receive(ChangeState message) {
			Class<? extends State> stateClass = message.getStateClass();
			render(stateClass);
			showWindow();
		}
	}
	
	private class ShowWindow implements Subscriber<TrayClick> {
		@Override
		public void receive(TrayClick message) {
			showWindow(message.mouseLocation());
		}
	}
	
}
