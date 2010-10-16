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

import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;

import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.ioc.Inject;
import org.tomighty.sound.Sound;
import org.tomighty.sound.SoundPlayer;
import org.tomighty.time.CountdownTimer;
import org.tomighty.time.CountdownTimerListener;
import org.tomighty.time.Time;
import org.tomighty.ui.UiState;
import org.tomighty.ui.state.laf.SexyLabel;

public abstract class TimerSupport extends UiStateSupport implements CountdownTimerListener {

	@Inject private CountdownTimer timer;
	@Inject private SoundPlayer soundPlayer;
	private JLabel remainingTime;

	protected abstract Time initialTime();
	protected abstract Class<? extends UiState> finishedState();
	protected abstract Class<? extends UiState> interruptedState();
	protected abstract Sound finishSound();
	
	@Override
	protected Component createContent() {
		Time time = initialTime();
		timer.start(time, this);
		remainingTime = SexyLabel.big(time.toString());
		return remainingTime;
	}

	@Override
	protected Action[] primaryActions() {
		return new Action[] {
			new Interrupt()
		};
	}

	@Override
	public void tick(final Time time) {
		invokeLater(new Runnable() {
			@Override
			public void run() {
				remainingTime.setText(time.toString());
			}
		});
	}
	
	@Override
	public void countdownFinished() {
		soundPlayer.play(finishSound());
		bus.publish(new ChangeUiState(finishedState()));
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
