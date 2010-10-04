package org.tomighty.ui.options;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.tomighty.config.Options;
import org.tomighty.ioc.Inject;
import org.tomighty.util.Images;

@SuppressWarnings("serial")
public class OptionsDialog extends JDialog {

	private static final int MARGIN_SIZE = 10;
	
	@Inject private Options options;
	private JPanel contentPane;
	private JCheckBox autoHideOption;
	
	public OptionsDialog() {
		createContentPane();
		configureDialog();
		pack();
		setLocationRelativeTo(null);
	}

	public void showDialog() {
		setVisible(true);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			autoHideOption.setSelected(options.autoHide());
		}
		super.setVisible(visible);
	}
	
	private void configureDialog() {
		setTitle("Options");
		setContentPane(contentPane);
		setIconImage(Images.get("/tomato-16x16.png"));
		setResizable(false);
	}

	private void createContentPane() {
		contentPane = new JPanel(new BorderLayout(0, MARGIN_SIZE));
		contentPane.setBorder(BorderFactory.createEmptyBorder(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE));
		contentPane.add(options(), BorderLayout.CENTER);
		contentPane.add(button(), BorderLayout.SOUTH);
	}

	private Component options() {
		autoHideOption = new JCheckBox("Hide window when focus is lost");
		JPanel panel = new JPanel();
		panel.add(autoHideOption);
		return panel;
	}

	private Component button() {
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		save.addActionListener(new Save());
		cancel.addActionListener(new Cancel());
		getRootPane().setDefaultButton(save);
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(save);
		panel.add(cancel);
		return panel;
	}

	private class Save implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			options.autoHide(autoHideOption.isSelected());
			setVisible(false);
		}
	}
	
	private class Cancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	
}
