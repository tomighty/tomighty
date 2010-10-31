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

import javax.swing.JCheckBox;

import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;

@SuppressWarnings("serial")
public class Sounds extends OptionPanel implements OptionGroup, Initializable {
	
	@Inject private Options options;
	@Inject private Messages messages;

	private JCheckBox wind = new JCheckBox();
	private JCheckBox tictac = new JCheckBox();
	private JCheckBox ding = new JCheckBox();

	public Sounds() {
		add(wind);
		add(tictac);
		add(ding);
	}
	
	@Override
	public void initialize() {
		wind.setText(messages.get("Enable wind sound"));
		tictac.setText(messages.get("Enable tic-tac"));
		ding.setText(messages.get("Enable ding sound"));
	}

	@Override
	public String name() {
		return messages.get("Sounds");
	}

	@Override
	public Component asComponent() {
		return this;
	}

	@Override
	public void readConfiguration() {
		wind.setSelected(options.sound().wind());
		tictac.setSelected(options.sound().tictac());
		ding.setSelected(options.sound().ding());
	}

	@Override
	public void saveConfiguration() {
		options.sound().wind(wind.isSelected());
		options.sound().tictac(tictac.isSelected());
		options.sound().ding(ding.isSelected());
	}

}
