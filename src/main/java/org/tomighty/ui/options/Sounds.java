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
import java.awt.LayoutManager;

import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;
import org.tomighty.ioc.Initializable;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.layout.StackLayout;
import org.tomighty.ui.util.CheckableFileField;

@SuppressWarnings("serial")
public class Sounds extends OptionPanel implements OptionGroup, Initializable {
	
	@Inject private Options options;
	@Inject private Messages messages;
	
	private CheckableFileField wind;
	private CheckableFileField tictac;
	private CheckableFileField ding;

	public Sounds() {
		add(wind = new CheckableFileField());
		add(tictac = new CheckableFileField());
		add(ding = new CheckableFileField());
	}
	
	@Override
	public void initialize() {
		wind.text(messages.get("Enable wind sound"));
		tictac.text(messages.get("Enable tic-tac"));
		ding.text(messages.get("Enable ding sound"));
	}
	
	@Override
	protected LayoutManager createLayout() {
		return new StackLayout(10);
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
		wind.checked(options.sound().wind());
		tictac.checked(options.sound().tictac());
		ding.checked(options.sound().ding());
	}

	@Override
	public void saveConfiguration() {
		options.sound().wind(wind.isChecked());
		options.sound().tictac(tictac.isChecked());
		options.sound().ding(ding.isChecked());
	}

}
