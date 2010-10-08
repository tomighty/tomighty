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

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.time.Time;
import org.tomighty.time.Timer;
import org.tomighty.time.TimerListener;
import org.tomighty.ui.UiState;
import org.tomighty.ui.widget.TextPanel;

public abstract class TimerSupport extends UiStateSupport implements TimerListener {

	private TextPanel remainingTime;
	private Timer timer;

	protected abstract Time initialTime();
	protected abstract Class<? extends UiState> finishedState();
	protected abstract Class<? extends UiState> interruptedState();
	
	@Override
	protected Component createContent() {
		Time time = initialTime();
		timer = new Timer(title());
		timer.listener(this);
		timer.start(time);
		remainingTime = LabelFactory.big(time.toString());
		return remainingTime;
	}

	@Override
	protected Action[] primaryActions() {
		return new Action[] {
			new Interrupt()
		};
	}

	@Override
	public void tick(Time time) {
		if(time.isZero()) {
			bus.publish(new ChangeUiState(finishedState()));
		} else {
			remainingTime.setText(time.toString());
		}
	}

	@SuppressWarnings("serial")
	private class Interrupt extends AbstractAction {
		public Interrupt() {
			super("Interrupt");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			timer.stop();
			bus.publish(new ChangeUiState(interruptedState()));
		}
	}

}
