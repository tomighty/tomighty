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

package org.tomighty.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.TrayClick;
import org.tomighty.config.Configuration;
import org.tomighty.ioc.Container;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.about.AboutDialog;
import org.tomighty.ui.options.OptionsDialog;
import org.tomighty.util.Images;

public class Tray implements Runnable {

	@Inject private Container container;
	@Inject private Configuration config;
	@Inject private Bus bus;
	
	@Override
	public void run() {
		TrayIcon icon = new TrayIcon(Images.get("/tomato-16x16.png"));
		icon.addMouseListener(new TrayListener());
		icon.setToolTip("Click to show/hide Tomighty");
		icon.setPopupMenu(createMenu());
		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(icon);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		boolean firstRun = config.asBoolean("firstRun", true);
		if(firstRun) {
			icon.displayMessage("First time using Tomighty?", "Click here to show it", MessageType.INFO);
			config.set("firstRun", false);
		}
	}

	private PopupMenu createMenu() {
		PopupMenu menu = new PopupMenu();
		menu.add(menuItem("Options", new Options()));
		menu.add(menuItem("About", new About()));
		menu.addSeparator();
		menu.add(menuItem("Close", new Exit()));
		return menu;
	}

	private MenuItem menuItem(String text, ActionListener listener) {
		MenuItem item = new MenuItem(text);
		item.addActionListener(listener);
		return item;
	}
	
	private class TrayListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				bus.publish(new TrayClick(e.getLocationOnScreen()));
			}
		}
	}
	
	private class Options implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			OptionsDialog dialog = container.get(OptionsDialog.class);
			dialog.showDialog();
		}
	}
	
	private class About implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AboutDialog about = container.get(AboutDialog.class);
			about.showDialog();
		}
	}
	
	private class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}
