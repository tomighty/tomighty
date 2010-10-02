package org.tomighty.ui.states;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.tomighty.ui.UiState;

public abstract class UiStateSupport implements UiState {

	protected final JPanel panel;

	public UiStateSupport() {
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
	}

}
