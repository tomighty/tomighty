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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

import org.tomighty.ioc.Inject;
import org.tomighty.ui.state.laf.look.Colors;
import org.tomighty.ui.state.laf.look.Look;

public class SexyLabelUI extends BasicLabelUI {

	@Inject private Look look;
	
	@Override
	public void paint(Graphics g, JComponent c) {
		JLabel label = (JLabel)c;
		String text = label.getText();
		if(text == null || text.trim().length() == 0) {
			return;
		}
		new TextRenderer(label).render((Graphics2D) g);
	}
	
	private class TextRenderer {
		
		private final JLabel label;

		public TextRenderer(JLabel label) {
			this.label = label;
		}

		public void render(Graphics2D g) {
			configureGraphics(g);
			WrappedLines lines = breakLines(g);
			drawLines(lines, g);
		}

		private void configureGraphics(Graphics2D g) {
			Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
			if(desktopHints != null) {
				g.setRenderingHints(desktopHints);
			}
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		
		private WrappedLines breakLines(Graphics2D g) {
			Dimension size = label.getSize();
			float maxWidth = size.width;
			AttributedString attributedText = new AttributedString(label.getText(), textAttributes());
			AttributedCharacterIterator textIterator = attributedText.getIterator();
			FontRenderContext fontRendering = g.getFontRenderContext();
			LineBreakMeasurer measurer = new LineBreakMeasurer(textIterator, fontRendering);
			WrappedLines lines = new WrappedLines();
			while(measurer.getPosition() < textIterator.getEndIndex()) {
				TextLayout layout = measurer.nextLayout(maxWidth);
				lines.add(layout);
			}
			return lines;
		}

		private Map<Attribute, Object> textAttributes() {
			Map<Attribute, Object> attributes = new HashMap<Attribute, Object>();
			attributes.putAll(label.getFont().getAttributes());
			attributes.put(TextAttribute.FONT, font());
			return attributes;
		}

		private Font font() {
			Font originalFont = label.getFont();
			if(!canRenderAllCharacters(originalFont)) {
				Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				for(Font anotherFont : allFonts) {
					if(canRenderAllCharacters(anotherFont)) {
						return anotherFont;
					}
				}
			}
			return originalFont;
		}

		private boolean canRenderAllCharacters(Font font) {
			return false;
		}

		private void drawLines(WrappedLines lines, Graphics2D g) {
			Colors colors = look.colors();
			float y = originY(lines);
			for(TextLayout line : lines.list) {
				float x = xFor(line);
				y += line.getAscent() - line.getDescent();
				Color shadowColor = colors.shadow();
				g.setColor(shadowColor);
				line.draw(g, x-1, y-1);
				g.setColor(colors.text());
				line.draw(g, x, y);
				y += line.getDescent();
			}
		}

		private float xFor(TextLayout line) {
			return (float) (label.getSize().getWidth() / 2.0 - line.getAdvance() / 2f);
		}

		private float originY(WrappedLines lines) {
			return (float) (label.getSize().getHeight() / 2.0 - lines.height / 2f);
		}
		
	}
	
	private class WrappedLines {
		
		List<TextLayout> list = new ArrayList<TextLayout>();
		float width = 0f;
		float height = 0f;
		
		public void add(TextLayout line) {
			float advance = line.getAdvance();
			if(advance > width) width = advance;
			height += line.getAscent();
			list.add(line);
		}

	}

}
