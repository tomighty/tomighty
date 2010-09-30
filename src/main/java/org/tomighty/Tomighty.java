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

import java.awt.AWTException;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.states.InitialState;
import org.tomighty.ui.Window;
import org.tomighty.util.Images;
import org.tomighty.util.New;

public class Tomighty {
	
	private Window window;
	private Logger logger;
	private TrayIcon trayIcon;
	private SystemTray tray;

	public Tomighty() throws AWTException {
		logger = Logger.getLogger(getClass().getName());
		
		window = new Window();
		
		trayIcon = new TrayIcon(Images.get("/tomato-16x16.png"));
		trayIcon.addMouseListener(new TrayClick());
		trayIcon.setToolTip("Click to show/hide Tomighty");
		
		tray = SystemTray.getSystemTray();
		tray.add(trayIcon);
		
		MenuItem closeItem = new MenuItem("Close");
		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(closeItem);
		closeItem.addActionListener(new Exit());
		trayIcon.setPopupMenu(popupMenu);
	}
	
	public void start() {
		Bus.subscribe(new StateSwitch(), ChangeState.class);
		render(InitialState.class);
		trayIcon.displayMessage(null, "Click here to show Tomighty", MessageType.NONE);
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

	private class StateSwitch implements Subscriber<ChangeState> {
		@Override
		public void receive(ChangeState message) {
			Class<? extends State> stateClass = message.getStateClass();
			render(stateClass);
			showWindow();
		}
	}
	
	private class TrayClick extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				showWindow(e.getLocationOnScreen());
			}
		}
	}
	
	private class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Tomighty().start();
	}

}
