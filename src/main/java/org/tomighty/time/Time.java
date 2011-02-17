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

package org.tomighty.time;

public class Time {
	
	private int seconds;
	
	private Time() {}

	public Time(int minutes) {
		this(minutes, 0);
	}

	public Time(int minutes, int seconds) {
		this.seconds = minutes * 60 + seconds;
	}

	public boolean isZero() {
		return seconds == 0;
	}

	public Time minusOneSecond() {
		Time time = new Time();
		time.seconds = this.seconds - 1;
		return time;
	}
	
	@Override
	public String toString() {
		int minutes = minutes();
		int secs = seconds();
		return (minutes < 10 ? "0" : "") + minutes + ":"
				+ (secs < 10 ? "0" : "") + secs;
	}

	public int minutes() {
		return (int) Math.floor((double) seconds / 60.0);
	}

	public int seconds() {
		return seconds % 60;
	}

}
