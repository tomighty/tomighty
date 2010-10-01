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
import org.tomighty.util.Images;

public class Tray {

	public void start() {
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
		icon.displayMessage(null, "Click here to show Tomighty", MessageType.NONE);
	}

	private PopupMenu createMenu() {
		MenuItem closeItem = new MenuItem("Close");
		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(closeItem);
		closeItem.addActionListener(new Exit());
		return popupMenu;
	}
	
	private class TrayListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				Bus.publish(new TrayClick(e.getLocationOnScreen()));
			}
		}
	}
	
	private class Exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}
