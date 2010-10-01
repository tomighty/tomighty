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

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

import org.tomighty.util.Images;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private Panel panel = new Panel();

	public Window() {
		super("Tomighty");
		setAlwaysOnTop(true);
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setResizable(false);
		setSize(152, 102);
		setUndecorated(true);
		setIconImage(Images.get("/tomato-48x48.png"));
		addWindowFocusListener(new HideWindowWhenLosingFocus());
	}

	public void setComponent(Component component) {
		panel.setComponent(component);
	}
	
	private class HideWindowWhenLosingFocus implements WindowFocusListener {
		@Override
		public void windowGainedFocus(WindowEvent e) {}
		
		@Override
		public void windowLostFocus(WindowEvent e) {
			setVisible(false);
		}
	}
	
}
