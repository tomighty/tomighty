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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import javax.swing.UIManager;

public class Canvas {

	private BufferedImage image;
	private int width;
	private int height;
	private Font font;

	public Canvas(Dimension size) {
		image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		this.width = size.width;
		this.height = size.height;
		this.font = defaultFont();
	}
	
	public static Font defaultFont() {
		return UIManager.getDefaults().getFont("Label.font");
	}

	public Image image() {
		return image;
	}
	
	public void fontSize(float size) {
		font = font.deriveFont(size);
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public void paintShinyBackground(Color bright, Color dark) {
		
		paintGradient(dark, bright);
		
		double ellipseWidth = (double)width * 1.5;
		double ellipseHeight = (double)height * 1.1;
		double x = (double)width / 2.0 - ellipseWidth / 2.0;
		double y = ellipseHeight * -0.55;
		Ellipse2D shape = new Ellipse2D.Double(x, y, ellipseWidth, ellipseHeight);
		Graphics2D graphics = image.createGraphics();
		try {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setPaint(new GradientPaint(0f, 0f, bright.brighter(), 0f, (float)shape.getHeight(), dark.brighter()));
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
			graphics.fill(shape);
		} finally {
			graphics.dispose();
		}
	}

	public void paintGradient(Color color) {
		Color start = color.brighter().brighter();
		Color end = color.darker().darker();
		paintGradient(start, end);
	}

	private void paintGradient(Color start, Color end) {
		paintGradient(start, end, 0, height - 1);
	}

	private void paintGradient(Color start, Color end, int y1, int y2) {
		Graphics2D graphics = image.createGraphics();
		try {
			graphics.setPaint(new GradientPaint(0, y1, start, 0, y2, end));
			graphics.fillRect(0, y1, width - 1, y2);
		} finally {
			graphics.dispose();
		}
	}

	public void drawBorder(Color color) {
		Graphics2D graphics = image.createGraphics();
		try {
			graphics.setColor(color);
			graphics.drawRect(0, 0, width - 1, height - 1);
		} finally {
			graphics.dispose();
		}
	}

	public void drawCentralizedText(String text) {
		Graphics2D graphics = image.createGraphics();
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

	public void applyFilter(BufferedImageOp filter) {
		image = filter.filter(image, image);
	}

	public void paint(Image image) {
		Graphics2D graphics = this.image.createGraphics();
		try {
			graphics.drawImage(image, 0, 0, null);
		} finally {
			graphics.dispose();
		}
	}

}
