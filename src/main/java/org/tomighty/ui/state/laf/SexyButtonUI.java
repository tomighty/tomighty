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

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

public class SexyButtonUI extends BasicButtonUI {

	public static final ButtonUI INSTANCE = new SexyButtonUI();
	
	private static final Font FONT = UIManager.getFont("Button.font").deriveFont(11f);
	private static final Color BG_LIGHT = new Color(230, 230, 230);
	private static final Color BG_DARK = new Color(150, 150, 150);
	private static final Color TEXT_LIGHT = new Color(210, 210, 210);
	private static final Color TEXT_DARK = new Color(40, 40, 40);
	private static final Color BORDER_COLOR = new Color(60, 60, 60);

	@Override
	public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
		Dimension size = button.getSize();
		if (!model.isPressed() || !model.isArmed()) {
			paintButtonUnpressed(g, size);
		}
		super.paint(g, button);
		paintBorder(g, size);
	}
	
	@Override
	protected void paintText(Graphics g, AbstractButton button, Rectangle textRect, String text) {
		Graphics2D g2d = createGraphics(g);
		try {
			g2d.setFont(FONT);
			FontMetrics metrics = g2d.getFontMetrics();
			int width = metrics.stringWidth(text);
			int descent = metrics.getDescent();
			int x = button.getWidth() / 2 - width / 2 + 1;
			int y = button.getHeight() / 2 + descent + 1;
			g2d.setColor(TEXT_LIGHT);
			g2d.drawString(text, ++x, ++y);
			g2d.setColor(TEXT_DARK);
			g2d.drawString(text, --x, --y);
		} finally {
			g2d.dispose();
		}
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		paintButton(g, b.getSize(), BG_DARK, BG_LIGHT);
	}
	
	private void paintButtonUnpressed(Graphics g, Dimension size) {
		paintButton(g, size, BG_LIGHT, BG_DARK);
	}
	
	private void paintButton(Graphics g, Dimension size, Color startColor, Color endColor) {
		Graphics2D g2d = createGraphics(g);
		try {
			g2d.setPaint(new GradientPaint(0, 0, startColor, 0, size.height, endColor));
			g2d.fillRoundRect(0, 0, size.width-1, size.height-1, 9, 9);
		} finally {
			g2d.dispose();
		}
	}

	private void paintBorder(Graphics g, Dimension size) {
		Graphics2D g2d = createGraphics(g);
		try {
			g2d.setStroke(new BasicStroke(2f));
			g2d.setColor(BORDER_COLOR);
			g2d.drawRoundRect(0, 0, size.width-1, size.height-1, 9, 9);
		} finally {
			g2d.dispose();
		}
	}

	private Graphics2D createGraphics(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		return g2d;
	}

}
