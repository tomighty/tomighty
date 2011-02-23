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

package org.tomighty.ui;

import java.awt.AWTException;
import java.awt.Image;
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
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.config.TimeOnTrayConfigChanged;
import org.tomighty.bus.messages.time.TimerStop;
import org.tomighty.bus.messages.time.TimerTick;
import org.tomighty.bus.messages.ui.TrayClick;
import org.tomighty.config.Configuration;
import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Container;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.resources.TrayIcons;
import org.tomighty.time.Time;
import org.tomighty.ui.about.AboutDialog;
import org.tomighty.ui.options.OptionsDialog;

public class Tray implements Runnable, Initializable {

	@Inject private Container container;
	@Inject private Configuration config;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Messages messages;
	@Inject private TrayIcons icons;
	private TrayIcon trayIcon;
	
	@Override
	public void initialize() {
		bus.subscribe(new UpdateTimeOnTray(), TimerTick.class);
		bus.subscribe(new ShowTomato(), TimerStop.class);
		bus.subscribe(new RemoveTimeFromTray(), TimeOnTrayConfigChanged.class);
		trayIcon = new TrayIcon(icons.tomato());
		trayIcon.addMouseListener(new TrayListener());
		trayIcon.setPopupMenu(createMenu());
	}
	
	@Override
	public void run() {
		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		boolean firstRun = config.asBoolean("firstRun", true);
		if(firstRun) {
			showWelcomeMessage(trayIcon);
			config.set("firstRun", false);
		}
	}

	private void showWelcomeMessage(TrayIcon icon) {
		String caption = messages.get("First time using Tomighty?");
		String message = messages.get("Click on the tomato icon to start using it");
		icon.displayMessage(caption, message, MessageType.INFO);
	}

	private PopupMenu createMenu() {
		PopupMenu menu = new PopupMenu();
		menu.add(menuItem(messages.get("Options"), new ShowOptions()));
		menu.add(menuItem(messages.get("About"), new About()));
		menu.addSeparator();
		menu.add(menuItem(messages.get("Close"), new Exit()));
		return menu;
	}

	private MenuItem menuItem(String text, ActionListener listener) {
		MenuItem item = new MenuItem(text);
		item.addActionListener(listener);
		return item;
	}
	
	private void showTomatoIcon() {
		Image image = icons.tomato();
		trayIcon.setImage(image);
	}

	private class TrayListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				bus.publish(new TrayClick(e.getLocationOnScreen()));
			}
		}
	}
	
	private class ShowOptions implements ActionListener {
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
	
	private class UpdateTimeOnTray implements Subscriber<TimerTick> {
		@Override
		public void receive(TimerTick tick) {
			if(options.ui().showTimeOnTray()) {
				Time time = tick.time();
				Image image = icons.time(time);
				trayIcon.setImage(image);
			}
		}
	}
	
	private class RemoveTimeFromTray implements Subscriber<TimeOnTrayConfigChanged> {
		@Override
		public void receive(TimeOnTrayConfigChanged configuration) {
			if(!configuration.shouldShowTimeOnTray()) {
				showTomatoIcon();
			}
		}
	}
	
	private class ShowTomato implements Subscriber<TimerStop> {
		@Override
		public void receive(TimerStop end) {
			showTomatoIcon();
		}
	}

}
