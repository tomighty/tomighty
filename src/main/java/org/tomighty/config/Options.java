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

package org.tomighty.config;

import org.tomighty.ioc.Inject;

public class Options {
	
	private static final String TIME_POMODORO = "option.time.pomodoro";
	private static final String TIME_SHORT_BREAK = "option.time.shortBreak";
	private static final String TIME_LONG_BREAK = "option.time.longBreak";
	private static final String UI_AUTOHIDE_WINDOW = "option.ui.window.autohide";
	private static final String SOUND_WIND = "option.sound.timer.wind.enable";
	private static final String SOUND_TICTAC = "option.sound.timer.tictac.enable";
	private static final String SOUND_DING = "option.sound.timer.ding.enable";
	
	@Inject private Configuration config;
	
	private UserInterfaceOptions ui = new UserInterfaceOptions();
	private SoundOptions sound = new SoundOptions();
	private TimeOptions time = new TimeOptions();
	
	public UserInterfaceOptions ui() {
		return ui;
	}
	
	public SoundOptions sound() {
		return sound;
	}

	public TimeOptions time() {
		return time;
	}
	
	public class TimeOptions {

		public int pomodoro() {
			return config.asInt(TIME_POMODORO, 25);
		}

		public int shortBreak() {
			return config.asInt(TIME_SHORT_BREAK, 5);
		}

		public int longBreak() {
			return config.asInt(TIME_LONG_BREAK, 15);
		}

		public void pomodoro(int minutes) {
			config.set(TIME_POMODORO, minutes);
		}

		public void shortBreak(int minutes) {
			config.set(TIME_SHORT_BREAK, minutes);
		}

		public void longBreak(int minutes) {
			config.set(TIME_LONG_BREAK, minutes);
		}
	}
	
	public class UserInterfaceOptions {
		public boolean autoHideWindow() {
			return config.asBoolean(UI_AUTOHIDE_WINDOW, true);
		}
		
		public void autoHide(boolean autoHide) {
			config.set(UI_AUTOHIDE_WINDOW, autoHide);
		}
	}
	
	public class SoundOptions {
		public boolean wind() {
			return config.asBoolean(SOUND_WIND, true);
		}

		public boolean tictac() {
			return config.asBoolean(SOUND_TICTAC, true);
		}

		public boolean ding() {
			return config.asBoolean(SOUND_DING, true);
		}

		public void wind(boolean enable) {
			config.set(SOUND_WIND, enable);
		}

		public void tictac(boolean enable) {
			config.set(SOUND_TICTAC, enable);
		}

		public void ding(boolean enable) {
			config.set(SOUND_DING, enable);
		}
	}

}
