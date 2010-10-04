package org.tomighty.ui.state;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;

public abstract class UiStateSupport implements UiState {

	@Inject protected Bus bus;
	protected final JPanel panel;

	public UiStateSupport() {
		panel = createPanel();
	}
	
	protected static final JPanel createPanel() {
		return createPanel(new BorderLayout());
	}
	
	protected static final JPanel createPanel(LayoutManager layout) {
		JPanel panel = new JPanel(layout);
		panel.setOpaque(false);
		return panel;
	}
	
	protected static final JButton createButton(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		button.setOpaque(false);
		return button;
	}

}
