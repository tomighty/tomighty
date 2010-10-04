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

package org.tomighty.ui.state;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.util.Images;

public class InitialState extends UiStateSupport implements ActionListener {

	@Override
	public Component render() throws Exception {
		panel.add(tomato(), BorderLayout.CENTER);
		panel.add(createButton("Start Pomodoro", this), BorderLayout.SOUTH);
		return panel;
	}

	private Component tomato() throws IOException {
		Image image = Images.get("/tomato-48x48.png");
		ImageIcon imageIcon = new ImageIcon(image);
		return new JLabel(imageIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bus.publish(new ChangeUiState(Pomodoro.class));
	}

}
