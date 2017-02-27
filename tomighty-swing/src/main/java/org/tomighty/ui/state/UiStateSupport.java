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

package org.tomighty.ui.state;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.inject.Inject;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.tomighty.bus.Bus;
import org.tomighty.i18n.Messages;
import org.tomighty.ui.UiState;
import org.tomighty.ui.layout.DockLayout;
import org.tomighty.ui.layout.Docking;
import org.tomighty.ui.menu.MenuButtonFactory;
import org.tomighty.ui.swing.gauge.Gauge;
import org.tomighty.ui.swing.laf.SexyButtonUI;
import org.tomighty.ui.swing.laf.SexyLabel;
import org.tomighty.ui.theme.Colors;

import com.google.inject.Injector;

public abstract class UiStateSupport implements UiState {

	@Inject private Injector injector;
	@Inject private Gauge gauge;
    @Inject private MenuButtonFactory menuButtonFactory;

    @Inject protected SexyLabel labelFactory;
	@Inject protected Bus bus;
	@Inject protected Messages messages;

	protected abstract String title();
	protected abstract Component createContent();
	protected abstract Action[] primaryActions();
	protected abstract Action[] secondaryActions();
	
	protected boolean displaysGauge() {
		return false;
	}
	
	@Override
	public final Component render() throws Exception {
		JPanel component = createComponent();
        JButton menuButton = menuButtonFactory.create(secondaryActions());
        JPanel panel = createPanel(new DockLayout());
        panel.add(menuButton, Docking.rightTop());
        panel.add(component, Docking.fill());
        return panel;
    }
	
	@Override
	public Colors colors() {
		return null;
	}
	
	@Override
	public void afterRendering() {
	}
	
	@Override
	public void beforeDetaching() {
	}
	
	private JPanel createComponent() {
		JPanel component = createPanel();
		component.add(createHeader(), NORTH);
		component.add(createContent(), CENTER);
		component.add(createButtons(), SOUTH);
		return component;
	}
	
	private JPanel createHeader() {
		JPanel panel = createPanel(new BorderLayout(0, 3));
		String title = title();
		if(title != null) {
			panel.add(labelFactory.small(title), CENTER);
		}
		if(displaysGauge()) {
			panel.add(gauge, SOUTH);
		}
		return panel;
	}
	
	private Component createButtons() {
		Action[] actions = primaryActions();
		JPanel buttons = createPanel(new GridLayout(1, actions.length, 3, 0));
		for(Action action : actions) {
			injector.injectMembers(action);
			JButton button = new JButton(action);
			button.setOpaque(false);
			button.setUI(SexyButtonUI.INSTANCE);
			buttons.add(button);
		}
		return buttons;
	}

	private JPanel createPanel() {
		return createPanel(new BorderLayout());
	}
	
	private static final JPanel createPanel(LayoutManager layout) {
		JPanel panel = new JPanel(layout);
		panel.setOpaque(false);
		return panel;
	}

}
