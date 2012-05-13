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

package org.tomighty.ui.tray;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.config.TimeOnTrayConfigChanged;
import org.tomighty.bus.messages.timer.TimerStopped;
import org.tomighty.bus.messages.timer.TimerTick;
import org.tomighty.bus.messages.ui.TrayClick;
import org.tomighty.config.Configuration;
import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.plugin.PluginManager;
import org.tomighty.resources.TrayIcons;
import org.tomighty.time.Time;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayManager implements Runnable {

	@Inject private Configuration config;
	@Inject private Options options;
	@Inject private Bus bus;
	@Inject private Messages messages;
	@Inject private TrayIcons icons;
    @Inject private PluginManager pluginManager;
    @Inject private TrayMenu trayMenu;
	private TrayIcon trayIcon;

    @PostConstruct
	public void initialize() {
		bus.subscribe(new UpdateTimeOnTray(), TimerTick.class);
		bus.subscribe(new ShowTomatoIconWhenTimerStops(), TimerStopped.class);
		bus.subscribe(new RemoveTimeFromTray(), TimeOnTrayConfigChanged.class);
		trayIcon = new TrayIcon(icons.tomato());
		trayIcon.addMouseListener(new TrayListener());
		trayIcon.setImageAutoSize(true);
        trayMenu.addTo(trayIcon);
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

	private class UpdateTimeOnTray implements Subscriber<TimerTick> {
		@Override
		public void receive(TimerTick tick) {
			if(options.ui().showTimeOnTray()) {
				Time time = tick.getTime();
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

	private class ShowTomatoIconWhenTimerStops implements Subscriber<TimerStopped> {
		@Override
		public void receive(TimerStopped end) {
			showTomatoIcon();
		}
	}

}
