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

import static org.tomighty.ui.Colors.DARK;
import static org.tomighty.ui.Colors.INNER_BORDER;
import static org.tomighty.ui.Colors.LIGHT;
import static org.tomighty.ui.Colors.OUTER_BORDER;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicPanelUI;

public class SexyPanelUI extends BasicPanelUI {

	public static final PanelUI INSTANCE = new SexyPanelUI();

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setLayout(new BorderLayout());
		c.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
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
		g2d.setColor(INNER_BORDER);
		g2d.drawRect(1, 1, width - 3, height - 3);
	}

	private void drawOuterBorder(Graphics2D g2d, int width, int height) {
		g2d.setColor(OUTER_BORDER);
		g2d.drawRect(0, 0, width - 1, height - 1);
	}

	private int paintBackground(Graphics2D g2d, int width, int height) {
		int half = height / 2;
		paintGradient(0, height, width, LIGHT, DARK, g2d);
		paintGradient(half, height, width, DARK, LIGHT, g2d);
		return height;
	}

	private void paintGradient(int startY, int endY, int width, Color startColor, Color endColor, Graphics2D g2d) {
		GradientPaint gp = new GradientPaint(0, startY, startColor, 0, endY, endColor);
		g2d.setPaint(gp);
		g2d.fillRect(0, startY, width, endY);
	}

}
