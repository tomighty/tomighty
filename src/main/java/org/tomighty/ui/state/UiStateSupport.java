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

import static java.awt.BorderLayout.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.ioc.Inject;
import org.tomighty.ioc.Injector;
import org.tomighty.ui.Label;
import org.tomighty.ui.UiState;

public abstract class UiStateSupport implements UiState {

	@Inject private Injector injector;
	@Inject protected Bus bus;
	
	protected abstract String title();
	protected abstract Component createContent();
	protected abstract Action[] primaryActions();
	
	@Override
	public final Component render() throws Exception {
		JPanel panel = createPanel(new BorderLayout());
		if(title() != null) {
			panel.add(createHeader(), NORTH);
		}
		panel.add(createContent(), CENTER);
		panel.add(createButtons(), SOUTH);
		return panel;
	}

	private Component createHeader() {
		return new Label(title());
	}

	private Component createButtons() {
		Action[] actions = primaryActions();
		JPanel buttons = createPanel(new GridLayout(1, actions.length, 3, 0));
		for(Action action : actions) {
			injector.inject(action);
			JButton button = new JButton(action);
			button.setOpaque(false);
			buttons.add(button);
		}
		return buttons;
	}
	
	private static final JPanel createPanel(LayoutManager layout) {
		JPanel panel = new JPanel(layout);
		panel.setOpaque(false);
		return panel;
	}

}
