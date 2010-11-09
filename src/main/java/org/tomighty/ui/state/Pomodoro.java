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

package org.tomighty.ui.state;

import javax.swing.Action;

import org.tomighty.config.Options;
import org.tomighty.ioc.Inject;
import org.tomighty.time.Time;
import org.tomighty.ui.UiState;
import org.tomighty.ui.state.laf.theme.ColorTone;

public class Pomodoro extends TimerSupport {
	
	@Inject private Options options;
	
	@Override
	public ColorTone colorTone() {
		return ColorTone.RED;
	}
	
	@Override
	protected String title() {
		return messages.get("Pomodoro");
	}

	@Override
	protected Time initialTime() {
		int minutes = options.time().pomodoro();
		return new Time(minutes);
	}
	
	@Override
	protected boolean displaysGauge() {
		return true;
	}

	@Override
	protected Class<? extends UiState> finishedState() {
		return PomodoroFinished.class;
	}

	@Override
	protected Class<? extends UiState> interruptedState() {
		return PomodoroInterrupted.class;
	}

	@Override
	protected Action[] secondaryActions() {
		return new Action[] {
			new ToState(messages.get("Restart pomodoro"), Pomodoro.class),
			new ToState(messages.get("Short break"), ShortBreak.class),
			new ToState(messages.get("Long break"), LongBreak.class)
		};
	}
	
}
