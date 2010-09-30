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

package org.tomighty.states;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tomighty.State;
import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.util.Images;

public class InitialState implements State, ActionListener {

	@Override
	public Component render() throws Exception {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(tomato(), BorderLayout.CENTER);
		panel.add(startButton(), BorderLayout.SOUTH);
		return panel;
	}

	private Component tomato() throws IOException {
		Image image = Images.get("/tomato-48x48.png");
		ImageIcon imageIcon = new ImageIcon(image);
		return new JLabel(imageIcon);
	}

	private Component startButton() {
		JButton startButton = new JButton("Start Pomodoro");
		startButton.addActionListener(this);
		return startButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Bus.publish(new ChangeState(Pomodoro.class));
	}

}
