/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty.ui.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.resources.Images;
import org.tomighty.resources.Text;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog implements Initializable {
	
	private static final int MARGIN = 10;
	
	@Inject private Messages messages;
	@Inject private Text text;
	@Inject private Images images;
	private JPanel panel;

	private JLabel title;
	private JTextArea license;
	private JButton closeButton;
	
	public AboutDialog() {
		createPanel();
		configureDialog();
	}
	
	@Override
	public void initialize() {
		title.setText("Tomighty "+text.projectVersion());
		license.setText(text.license());
		closeButton.setText(messages.get("Close"));
		setTitle(messages.get("About Tomighty"));
		setIconImages(images.tomatoes());
		pack();
		setLocationRelativeTo(null);
	}

	public void showDialog() {
		setVisible(true);
	}

	private void configureDialog() {
		setAlwaysOnTop(true);
		setContentPane(panel);
		setResizable(false);
	}

	private void createPanel() {
		panel = new JPanel(new BorderLayout(0, MARGIN));
		panel.setBorder(emptyBorder());
		panel.add(title(), BorderLayout.NORTH);
		panel.add(text(), BorderLayout.CENTER);
		panel.add(closeButton(), BorderLayout.SOUTH);
	}

	private Component title() {
		title = new JLabel();
		title.setHorizontalAlignment(JLabel.CENTER);
		JLabel url = new JLabel("http://tomighty.googlecode.com", JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(25f));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(title, BorderLayout.NORTH);
		panel.add(url, BorderLayout.SOUTH);
		return panel;
	}
	
	private Component text() {
		license = new JTextArea();
		license.setFont(getFont());
		license.setBackground(getBackground());
		license.setEditable(false);
		license.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY), 
				emptyBorder()));
		return license;
	}

	private Component closeButton() {
		closeButton = new JButton();
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(closeButton);
		return panel;
	}

	private Border emptyBorder() {
		return BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN);
	}
	
}
