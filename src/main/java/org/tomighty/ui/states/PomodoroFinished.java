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

package org.tomighty.ui.states;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.ui.LabelFactory;

public class PomodoroFinished extends UiStateSupport {

	@Override
	public Component render() throws Exception {
		panel.add(LabelFactory.small("Pomodoro finished"), BorderLayout.NORTH);
		panel.add(LabelFactory.medium("Take a break"), BorderLayout.CENTER);
		panel.add(buttons(), BorderLayout.SOUTH);
		return panel;
	}

	private Component buttons() {
		JPanel panel = createPanel(new GridLayout(1, 2, 3, 0));
		panel.add(shortBreak());
		panel.add(longBreak());
		return panel;
	}
	
	private JButton shortBreak() {
		return createButton("Short", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bus.publish(new ChangeUiState(ShortBreak.class));
			}
		});
	}

	private JButton longBreak() {
		return createButton("Long", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bus.publish(new ChangeUiState(LongBreak.class));
			}
		});
	}

}
