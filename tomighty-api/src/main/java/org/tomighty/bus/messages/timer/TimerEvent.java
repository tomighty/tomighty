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

package org.tomighty.bus.messages.timer;

import org.tomighty.Phase;
import org.tomighty.time.Time;

public abstract class TimerEvent {

	private final Time time;
    private final Phase phase;

    protected TimerEvent(Time time, Phase phase) {
		this.time = time;
        this.phase = phase;
    }

	public Time getTime() {
		return time;
	}

    public Phase getPhase() {
        return phase;
    }

}
