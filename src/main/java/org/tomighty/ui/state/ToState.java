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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.ChangeUiState;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;

@SuppressWarnings("serial")
public class ToState extends AbstractAction {
	
	@Inject private Bus bus;

	private final Class<? extends UiState> stateClass;

	public ToState(String name, Class<? extends UiState> stateClass) {
		super(name);
		this.stateClass = stateClass;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		bus.publish(new ChangeUiState(stateClass));
	}

}
