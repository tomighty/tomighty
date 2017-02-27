/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.ui.options;

import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JFormattedTextField;

import org.tomighty.config.Options;
import org.tomighty.i18n.Messages;

@SuppressWarnings("serial")
public class Times extends OptionPanel implements OptionGroup {

	@Inject private Options options;
	@Inject private Messages messages;

	private JFormattedTextField pomodoro;
	private JFormattedTextField shortBreak;
	private JFormattedTextField longBreak;

    @PostConstruct
	public void initialize() {
		pomodoro = addField("Pomodoro");
		shortBreak = addField("Short break");
		longBreak = addField("Long break");
	}

	@Override
	public String name() {
		return messages.get("Times");
	}

	@Override
	public Component asComponent() {
		return this;
	}

	@Override
	public void readConfiguration() {
		pomodoro.setValue(options.time().pomodoro());
		shortBreak.setValue(options.time().shortBreak());
		longBreak.setValue(options.time().longBreak());
	}

	@Override
	public void saveConfiguration() {
		options.time().pomodoro(valueOf(pomodoro));
		options.time().shortBreak(valueOf(shortBreak));
		options.time().longBreak(valueOf(longBreak));
	}

	private int valueOf(JFormattedTextField field) {
        return new OptionCommons(messages).valueOf(field);
	}

	private JFormattedTextField addField(String name) {
        return new OptionCommons(messages).addStandardOption(this, name, "minutes");
	}

}
