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

package org.tomighty.ui.state.breaks;

import javax.inject.Inject;
import javax.swing.Action;

import org.tomighty.config.Options;
import org.tomighty.time.Time;
import org.tomighty.ui.state.ToState;
import org.tomighty.ui.theme.Colors;
import org.tomighty.ui.theme.colors.Blue;
import org.tomighty.ui.state.pomodoro.Pomodoro;

public class LongBreak extends Break {

	@Inject private Options options;
	
	@Override
	public Colors colors() {
		return Blue.instance();
	}
	
	@Override
	protected String title() {
		return messages.get("Long break");
	}

	@Override
	protected Time initialTime() {
		int minutes = options.time().longBreak();
		return new Time(minutes);
	}

	@Override
	protected Action[] secondaryActions() {
		return new Action[] {
			new ToState(messages.get("Restart break"), LongBreak.class),
			new ToState(messages.get("Short break"), ShortBreak.class),
			new ToState(messages.get("New pomodoro"), Pomodoro.class)
		};
	}

}
