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

import org.tomighty.util.Images;
import org.tomighty.util.Resources;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	
	private static final int MARGIN = 10;
	
	private JPanel panel;
	
	public AboutDialog() {
		createPanel();
		configureDialog();
		pack();
		setLocationRelativeTo(null);
	}

	public void showDialog() {
		setVisible(true);
	}

	private void configureDialog() {
		setTitle("About Tomighty");
		setAlwaysOnTop(true);
		setContentPane(panel);
		setIconImage(Images.get("/tomato-16x16.png"));
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
		JLabel title = new JLabel("Tomighty", JLabel.CENTER);
		JLabel url = new JLabel("http://tomighty.googlecode.com", JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(25f));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(title, BorderLayout.NORTH);
		panel.add(url, BorderLayout.SOUTH);
		return panel;
	}
	
	private Component text() {
		String text = Resources.readText("/license.txt");
		JTextArea textArea = new JTextArea(text);
		textArea.setFont(getFont());
		textArea.setBackground(getBackground());
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY), 
				emptyBorder()));
		return textArea;
	}

	private Component closeButton() {
		JButton button = new JButton("Close");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(button);
		return panel;
	}

	private Border emptyBorder() {
		return BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN);
	}
	
}
