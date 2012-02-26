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

package org.tomighty.ui.options;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.google.inject.Injector;
import org.tomighty.i18n.Messages;
import org.tomighty.resources.Images;

@SuppressWarnings("serial")
public class OptionsDialog extends JDialog {

	private static final int MARGIN_SIZE = 10;
	
	private JPanel contentPane;
	private JTabbedPane tabs;
	private JButton saveButton;
	private JButton cancelButton;
	private List<OptionGroup> optionGroups = new ArrayList<OptionGroup>();
	
	@Inject private Injector injector;
	@Inject private Messages messages;
	@Inject private Images images;
	
	public OptionsDialog() {
		createContentPane();
		configureDialog();
	}

	@PostConstruct
	public void initialize() {
		saveButton.setText(messages.get("Save"));
		cancelButton.setText(messages.get("Cancel"));
		setTitle(messages.get("Options"));
		setIconImages(images.tomatoes());
		createOptionGroups();
		pack();
		setLocationRelativeTo(null);
	}

	private void createOptionGroups() {
		optionGroups.add(injector.getInstance(Times.class));
		optionGroups.add(injector.getInstance(UserInterface.class));
		optionGroups.add(injector.getInstance(Sounds.class));
		for(OptionGroup group : optionGroups) {
			tabs.addTab(group.name(), group.asComponent());
		}
	}

	public void showDialog() {
		setVisible(true);
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			for(OptionGroup group : optionGroups) {
				group.readConfiguration();
			}
		}
		super.setVisible(visible);
	}
	
	private void configureDialog() {
		setContentPane(contentPane);
		setResizable(false);
	}

	private void createContentPane() {
		contentPane = new JPanel(new BorderLayout(0, MARGIN_SIZE));
		contentPane.setBorder(BorderFactory.createEmptyBorder(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE));
		contentPane.add(options(), BorderLayout.CENTER);
		contentPane.add(button(), BorderLayout.SOUTH);
	}

	private Component options() {
		return tabs = new JTabbedPane();
	}

	private Component button() {
		saveButton = new JButton();
		cancelButton = new JButton();
		saveButton.addActionListener(new Save());
		cancelButton.addActionListener(new Cancel());
		getRootPane().setDefaultButton(saveButton);
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(saveButton);
		panel.add(cancelButton);
		return panel;
	}

	private class Save implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for(OptionGroup group : optionGroups) {
				group.saveConfiguration();
			}
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
