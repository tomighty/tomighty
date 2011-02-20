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

package org.tomighty.ui.util;

import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.UIManager;

import org.tomighty.ui.state.laf.theme.ColorTone;

public class Canvas {

	private int width;
	private int height;
	private Graphics2D originalGraphics;
	private Font font;

	public Canvas(Dimension size, Graphics2D graphics) {
		this.width = size.width;
		this.height = size.height;
		this.originalGraphics = graphics;
		this.font = UIManager.getDefaults().getFont("Label.font");
	}
	
	public void fontSize(float size) {
		font = font.deriveFont(size);
	}
	
	public void drawGradientBackground(ColorTone colors) {
		Color start = colors.light();
		Color end = colors.dark();
		Graphics2D graphics = (Graphics2D) originalGraphics.create();
		try {
			graphics.setPaint(new GradientPaint(0, 0, start, 0, height, end));
			graphics.fillRect(0, 0, width - 1, height - 1);
			
		} finally {
			graphics.dispose();
		}
	}
	
	public void drawBorder(Color color) {
		Graphics2D graphics = (Graphics2D) originalGraphics.create();
		try {
			graphics.setColor(color);
			graphics.drawRect(0, 0, width - 1, height - 1);
			
		} finally {
			graphics.dispose();
		}
	}

	public void drawCentralizedText(String text) {
		Graphics2D graphics = (Graphics2D) originalGraphics.create();
		try {
			graphics.setFont(font);
			graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
			
			FontRenderContext context = graphics.getFontRenderContext();
			TextLayout layout = new TextLayout(text, font, context);
			int x = width / 2 - (int) (layout.getAdvance() / 2f);
			int y = height / 2 + (int) (layout.getAscent() / 2f) - 1;
			
			graphics.setColor(Color.DARK_GRAY.darker());
			graphics.drawString(text, x-1, y-1);
			graphics.setColor(Color.WHITE);
			graphics.drawString(text, x, y);
		
		} finally {
			graphics.dispose();
		}
	}

}
