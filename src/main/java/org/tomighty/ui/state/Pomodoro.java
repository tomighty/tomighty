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

package org.tomighty.ui.state;

import javax.swing.Action;

import org.tomighty.time.Time;
import org.tomighty.time.CountdownTimerListener;
import org.tomighty.ui.UiState;

public class Pomodoro extends TimerSupport implements CountdownTimerListener {

	@Override
	protected String title() {
		return "Pomodoro";
	}

	@Override
	protected Time initialTime() {
		return new Time(25);
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
			new ToState("Restart pomodoro", Pomodoro.class),
			new ToState("Short break", ShortBreak.class),
			new ToState("Long break", LongBreak.class)
		};
	}
	
}
