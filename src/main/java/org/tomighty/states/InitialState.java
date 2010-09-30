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
