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

package org.tomighty.ui.state.laf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicPanelUI;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.ThemeChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.state.laf.theme.ColorTone;
import org.tomighty.ui.state.laf.theme.Theme;

public class SexyPanelUI extends BasicPanelUI implements Subscriber<ThemeChanged> {

	@Inject private Theme theme;
	private List<JComponent> installedComponents = new ArrayList<JComponent>();
	
	@Inject
	public SexyPanelUI(Bus bus) {
		bus.subscribe(this, ThemeChanged.class);
	}
	
	@Override
	public void receive(ThemeChanged message) {
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
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g;
		int width = c.getWidth();
		int height = c.getHeight();
		paintBackground(g2d, width, height);
		drawOuterBorder(g2d, width, height);
		drawInnerBorder(g2d, width, height);
	}

	private void drawInnerBorder(Graphics2D g2d, int width, int height) {
		ColorTone colorTone = theme.colorTone();
		g2d.setColor(colorTone.lightBorder());
		g2d.drawRect(1, 1, width - 3, height - 3);
	}

	private void drawOuterBorder(Graphics2D g2d, int width, int height) {
		ColorTone colorTone = theme.colorTone();
		g2d.setColor(colorTone.shadowBorder());
		g2d.drawRect(0, 0, width - 1, height - 1);
	}

	private int paintBackground(Graphics2D g2d, int width, int height) {
		ColorTone colorTone = theme.colorTone();
		int half = height / 2;
		paintGradient(0, height, width, colorTone.light(), colorTone.dark(), g2d);
		paintGradient(half, height, width, colorTone.dark(), colorTone.light(), g2d);
		return height;
	}

	private void paintGradient(int startY, int endY, int width, Color startColor, Color endColor, Graphics2D g2d) {
		GradientPaint gp = new GradientPaint(0, startY, startColor, 0, endY, endColor);
		g2d.setPaint(gp);
		g2d.fillRect(0, startY, width, endY);
	}

}
