package org.tomighty.states;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.State;
import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeState;
import org.tomighty.util.Label;

public class PomodoroInterrupted implements State, ActionListener {

	@Override
	public Component render() throws Exception {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(text(), BorderLayout.CENTER);
		panel.add(restartButton(), BorderLayout.SOUTH);
		return panel;
	}

	private Component text() {
		return Label.html("<html><font size='5'><center>" +
				"Pomodoro<p>" +
				"interrupted</html>");
	}

	private Component restartButton() {
		JButton button = new JButton("Restart");
		button.addActionListener(this);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Bus.publish(new ChangeState(Pomodoro.class));
	}

}
