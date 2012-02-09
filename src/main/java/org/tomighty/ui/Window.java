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

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.tomighty.config.Options;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.resources.Images;
import org.tomighty.ui.location.Closest;
import org.tomighty.ui.location.Location;
import org.tomighty.ui.swing.laf.SexyPanelUI;

@SuppressWarnings("serial")
public class Window extends JFrame implements Initializable {

	@Inject private Options options;
	@Inject private Images images;
	private JPanel panel = new JPanel();
	private WindowDragger dragger = new WindowDragger();
	private boolean gotRelocatedOnceAtLeast;

	@Inject
	public Window(SexyPanelUI panelUI) {
		super("Tomighty");
		panel.setUI(panelUI);
		setAlwaysOnTop(true);
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setResizable(false);
		setSize(152, 102);
		setUndecorated(true);
		addWindowFocusListener(new HideWindowWhenLosingFocus());
		addMouseListener(dragger);
		addMouseMotionListener(dragger);
	}
	
	@Override
	public void initialize() {
		setIconImages(images.tomatoes());
	}

	public void setComponent(Component component) {
		panel.removeAll();
		panel.add(component);
	}
	
	public void show(Point mouseLocation) {
		if(canRelocateWindow(mouseLocation)) {
			Location location = Closest.location(mouseLocation);
			Point point = location.determine(getSize());
			setLocation(point);
			gotRelocatedOnceAtLeast = true;
		}
		setVisible(true);
	}

	private boolean canRelocateWindow(Point mouseLocation) {
		if(options.ui().draggableWindow() && gotRelocatedOnceAtLeast) {
			return false;
		}
		return mouseLocation != null;
	}
	
	private class HideWindowWhenLosingFocus implements WindowFocusListener {
		@Override
		public void windowGainedFocus(WindowEvent e) {}
		
		@Override
		public void windowLostFocus(WindowEvent e) {
			if(options.ui().autoHideWindow()) {
				setVisible(false);
			}
		}
	}
	
	private class WindowDragger extends MouseAdapter {
		
		private Point offset;
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(leftClicked(e)) {
				offset = e.getPoint();
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(leftClicked(e)) {
				offset = null;
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(offset == null || !options.ui().draggableWindow()) {
				return;
			}
			Point mouseLocation = e.getLocationOnScreen();
			int x = mouseLocation.x - offset.x;
			int y = mouseLocation.y - offset.y;
			setLocation(x, y);
		}

		private boolean leftClicked(MouseEvent e) {
			return e.getButton() == MouseEvent.BUTTON1;
		}
		
	}
	
}
