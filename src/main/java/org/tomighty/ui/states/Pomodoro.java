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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.time.Time;
import org.tomighty.time.Timer;
import org.tomighty.time.TimerListener;
import org.tomighty.ui.Label;
import org.tomighty.ui.LabelFactory;
import org.tomighty.ui.UiState;

public class Pomodoro implements UiState, ActionListener, TimerListener {

	private Label remainingTime;
	private Timer timer;

	@Override
	public Component render() throws Exception {
		Time time = new Time(25);
		
		JButton interruptButton = new JButton("Interrupt");
		interruptButton.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(LabelFactory.small("Pomodoro"), BorderLayout.NORTH);
		panel.add(remainingTime(time), BorderLayout.CENTER);
		panel.add(interruptButton, BorderLayout.SOUTH);
		
		timer = new Timer("Pomodoro");
		timer.listener(this);
		timer.start(time);
		
		return panel;
	}
	
	private Component remainingTime(Time time) {
		remainingTime = LabelFactory.big(time.toString());
		return remainingTime;
	}

	@Override
	public void tick(Time time) {
		if(time.isZero()) {
			finished();
		} else {
			remainingTime.setText(time.toString());
		}
	}

	private void finished() {
		Bus.publish(new ChangeUiState(PomodoroFinished.class));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
		Bus.publish(new ChangeUiState(PomodoroInterrupted.class));
	}

}
