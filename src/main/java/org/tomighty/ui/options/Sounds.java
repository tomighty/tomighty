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

import org.tomighty.config.Options;
import org.tomighty.config.Options.SoundConfig;
import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Factory;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.swing.CheckableFileField;

@SuppressWarnings("serial")
public class Sounds extends OptionPanel implements OptionGroup, Initializable {
	
	@Inject private Options options;
	@Inject private Messages messages;
	
	private CheckableFileField wind;
	private CheckableFileField tictac;
	private CheckableFileField ding;

	@Inject
	public Sounds(Factory factory) {
		add(wind = factory.create(CheckableFileField.class));
		add(tictac = factory.create(CheckableFileField.class));
		add(ding = factory.create(CheckableFileField.class));
	}
	
	@Override
	public void initialize() {
		wind.text(messages.get("Enable wind sound"));
		tictac.text(messages.get("Enable tic-tac"));
		ding.text(messages.get("Enable ding sound"));
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
		readConfiguration(wind, options.sound().wind());
		readConfiguration(tictac, options.sound().tictac());
		readConfiguration(ding, options.sound().ding());
	}

	@Override
	public void saveConfiguration() {
		saveConfiguration(wind, options.sound().wind());
		saveConfiguration(tictac, options.sound().tictac());
		saveConfiguration(ding, options.sound().ding());
	}

	private void readConfiguration(CheckableFileField field, SoundConfig option) {
		field.checked(option.enabled());
		field.file(option.file());
	}

	private void saveConfiguration(CheckableFileField field, SoundConfig option) {
		option.enable(field.isChecked());
		option.file(field.file());
	}

}
