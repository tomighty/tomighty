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

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.MutableComboBoxModel;

import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.theme.Theme;
import org.tomighty.ui.theme.themes.BrushedMetal;
import org.tomighty.ui.theme.themes.Gradient;
import org.tomighty.ui.theme.themes.Grainy;
import org.tomighty.ui.theme.themes.Shiny;
import org.tomighty.util.FriendlyName;

@SuppressWarnings("serial")
public class UserInterface extends OptionPanel implements OptionGroup, Initializable {

	@Inject private Options options;
	@Inject private Messages messages;
	private JLabel themeLabel;
	private MutableComboBoxModel themeOptions;
	private JCheckBox autoHideOption;
	private JCheckBox draggableWindowOption;
	private JCheckBox showTimeOnTrayOption;

	public UserInterface() {
		autoHideOption = new JCheckBox();
		draggableWindowOption = new JCheckBox();
		showTimeOnTrayOption = new JCheckBox();
		add(createThemeOptions());
		add(autoHideOption);
		add(draggableWindowOption);
		add(showTimeOnTrayOption);
	}
	
	private JComponent createThemeOptions() {
		themeLabel = new JLabel();
		themeOptions = new DefaultComboBoxModel();
		themeOptions.addElement(new ThemeOption(BrushedMetal.class));
		themeOptions.addElement(new ThemeOption(Gradient.class));
		themeOptions.addElement(new ThemeOption(Grainy.class));
		themeOptions.addElement(new ThemeOption(Shiny.class));
		
		JPanel panel = new JPanel();
		panel.add(themeLabel);
		panel.add(new JComboBox(themeOptions));
		return panel;
	}
	
	@Override
	public void initialize() {
		themeLabel.setText(messages.get("Theme"));
		autoHideOption.setText(messages.get("Auto hide window"));
		draggableWindowOption.setText(messages.get("Allows dragging the window around"));
		showTimeOnTrayOption.setText(messages.get("Show remaining time on tray"));
	}

	@Override
	public String name() {
		return messages.get("User interface");
	}

	@Override
	public Component asComponent() {
		return this;
	}

	@Override
	public void readConfiguration() {
		autoHideOption.setSelected(options.ui().autoHideWindow());
		draggableWindowOption.setSelected(options.ui().draggableWindow());
		showTimeOnTrayOption.setSelected(options.ui().showTimeOnTray());
		themeOptions.setSelectedItem(new ThemeOption(options.ui().theme().getClass()));
	}

	@Override
	public void saveConfiguration() {
		options.ui().autoHide(autoHideOption.isSelected());
		options.ui().draggableWindow(draggableWindowOption.isSelected());
		options.ui().showTimeOnTray(showTimeOnTrayOption.isSelected());
		options.ui().theme(((ThemeOption)themeOptions.getSelectedItem()).clazz());
	}
	
	private class ThemeOption {

		private final Class<? extends Theme> clazz;

		public ThemeOption(Class<? extends Theme> clazz) {
			this.clazz = clazz;
		}
		
		public Class<? extends Theme> clazz() {
			return clazz;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof ThemeOption)) {
				return false;
			}
			return clazz.equals(((ThemeOption)obj).clazz);
		}
		
		@Override
		public String toString() {
			String name;
			FriendlyName friendlyName = clazz.getAnnotation(FriendlyName.class);
			if(friendlyName == null) {
				name = clazz.getSimpleName();
			} else {
				name = friendlyName.value();
			}
			return messages.get(name);
		}
		
	}

}
