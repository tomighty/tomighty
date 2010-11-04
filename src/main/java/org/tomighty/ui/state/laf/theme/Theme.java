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

package org.tomighty.ui.state.laf.theme;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ThemeChanged;
import org.tomighty.bus.messages.UiStateChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;

public class Theme implements Subscriber<UiStateChanged> {
	
	@Inject private Bus bus;
	private ColorTone currentColorTone = ColorTone.BLACK;

	@Inject
	public Theme(Bus bus) {
		bus.subscribe(this, UiStateChanged.class);
	}
	
	@Override
	public void receive(UiStateChanged message) {
		UiState uiState = message.uiState();
		ColorTone colorTone = uiState.colorTone();
		if(colorTone == null) {
			colorTone = ColorTone.BLACK;
		}
		changeColorTone(colorTone);
	}

	public ColorTone colorTone() {
		return currentColorTone;
	}

	private void changeColorTone(ColorTone colorTone) {
		if(currentColorTone.equals(colorTone)) {
			return;
		}
		currentColorTone = colorTone;
		bus.publish(new ThemeChanged());
	}

}
