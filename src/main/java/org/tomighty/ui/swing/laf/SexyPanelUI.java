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

package org.tomighty.ui.swing.laf;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ui.LookChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.theme.Look;
import org.tomighty.ui.theme.Theme;
import org.tomighty.ui.util.Canvas;

public class SexyPanelUI extends BasicPanelUI implements Subscriber<LookChanged> {

	@Inject private Look look;
	private List<JComponent> installedComponents = new ArrayList<JComponent>();
	
	@Inject
	public SexyPanelUI(Bus bus) {
		bus.subscribe(this, LookChanged.class);
	}
	
	@Override
	public void receive(LookChanged message) {
		for(JComponent c : installedComponents) {
			c.repaint();
		}
	}
	
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setLayout(new BorderLayout());
		c.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		installedComponents.add(c);
	}

	@Override
	public void paint(Graphics g, JComponent component) {
		Canvas canvas = new Canvas(component.getSize());
		Theme theme = look.theme();
		theme.paint(canvas);
		canvas.drawBorder(look.colors().shadow());
		g.drawImage(canvas.image(), 0, 0, null);
	}

}
