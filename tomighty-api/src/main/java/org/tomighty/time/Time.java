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

package org.tomighty.time;

public class Time {
	
	private int totalTimeInSeconds;
	
	private Time() {}

	public Time(int minutes) {
		this(minutes, 0);
	}

	public Time(int minutes, int seconds) {
		this.totalTimeInSeconds = minutes * 60 + seconds;
	}

    public static Time seconds(int seconds) {
        return new Time(0, seconds);
    }

    public static Time minutes(int minutes) {
        return new Time(minutes, 0);
    }

	public boolean isZero() {
		return totalTimeInSeconds == 0;
	}

	public Time minusOneSecond() {
		Time time = new Time();
		time.totalTimeInSeconds = this.totalTimeInSeconds - 1;
		return time;
	}

	public int minutes() {
		return (int) Math.floor((double) totalTimeInSeconds / 60.0);
	}

	public int seconds() {
		return totalTimeInSeconds % 60;
	}

	public String shortestString() {
		return String.valueOf(minutes() > 0 ? minutes() : seconds());
	}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Time))
            return false;

        Time anotherTime = (Time) obj;
        return totalTimeInSeconds == anotherTime.totalTimeInSeconds;
    }

    @Override
	public String toString() {
		int minutes = minutes();
		int secs = seconds();
		return (minutes < 10 ? "0" : "") + minutes + ":"
				+ (secs < 10 ? "0" : "") + secs;
	}

}
