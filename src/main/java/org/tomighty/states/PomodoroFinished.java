package org.tomighty.states;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.State;
import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.util.Label;

public class PomodoroFinished implements State {

	@Override
	public Component render() throws Exception {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(Label.small("Pomodoro finished"), BorderLayout.NORTH);
		panel.add(Label.medium("Take a break"), BorderLayout.CENTER);
		panel.add(buttons(), BorderLayout.SOUTH);
		return panel;
	}

	private Component buttons() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 3, 0));
		panel.add(shortBreak());
		panel.add(longBreak());
		return panel;
	}
	
	private JButton shortBreak() {
		JButton button = new JButton("Short");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bus.publish(new ChangeState(ShortBreak.class));
			}
		});
		return button;
	}

	private JButton longBreak() {
		JButton button = new JButton("Long");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bus.publish(new ChangeState(LongBreak.class));
			}
		});
		return button;
	}

}
