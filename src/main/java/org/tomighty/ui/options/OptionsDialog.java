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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.tomighty.ioc.Factory;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.util.Images;

@SuppressWarnings("serial")
public class OptionsDialog extends JDialog implements Initializable {

	private static final int MARGIN_SIZE = 10;
	
	private JPanel contentPane;
	private JTabbedPane tabs;
	
	@Inject private Factory factory;
	private List<OptionGroup> optionGroups = new ArrayList<OptionGroup>();
	
	public OptionsDialog() {
		createContentPane();
		configureDialog();
	}
	
	@Override
	public void initialize() {
		optionGroups.add(factory.create(Times.class));
		optionGroups.add(factory.create(UserInterface.class));
		optionGroups.add(factory.create(Sounds.class));
		for(OptionGroup group : optionGroups) {
			tabs.addTab(group.name(), group.asComponent());
		}
		pack();
		setLocationRelativeTo(null);
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
		return tabs = new JTabbedPane();
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
