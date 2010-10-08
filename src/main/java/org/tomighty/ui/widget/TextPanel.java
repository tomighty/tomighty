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

package org.tomighty.ui.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class TextPanel extends JComponent {

	private String text;
	private Color shadowColor;
	private boolean antialiasing = false;
	private float lineSpacing = 0f;
	private Padding padding = new Padding();
	private AlignX alignX = AlignX.LEFT;
	private AlignY alignY = AlignY.TOP;
	
	private boolean outdated = true;
	private Dimension preferredSize;
	private BufferedImage cache;
	
	public TextPanel() {
		this(null);
	}
	
	public TextPanel(String text) {
		setOpaque(false);
		setFont(UIManager.getDefaults().getFont("Label.font"));
		setText(text);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				outdated = true;
				preferredSize = null;
			}
		});
	}
	
	public void setText(String text) {
		this.text = text;
		render();
	}
	
	public void setShadowColor(Color color) {
		this.shadowColor = color;
		render();
	}

	public void setAntialiasing(boolean antialiasing) {
		this.antialiasing = antialiasing;
		render();
	}
	
	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
		render();
	}
	
	public void setPadding(Padding padding) {
		this.padding = padding;
		render();
	}
	
	public void setAlignX(AlignX alignX) {
		this.alignX = alignX;
		render();
	}
	
	public void setAlignY(AlignY alignY) {
		this.alignY = alignY;
		render();
	}

	private void render() {
		outdated = true;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(text == null || text.trim().length() == 0) {
			return;
		}
		if(outdated) {
			cache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D imageGraphics = cache.createGraphics();
			try {
				new TextRenderer().render(imageGraphics);
			} finally {
				imageGraphics.dispose();
			}
			outdated = false;
		}
		g.drawImage(cache, 0, 0, null);
	}

	private class TextRenderer {
		
		public void render(Graphics2D g) {
			configureGraphics(g);
			WrappedLines lines = breakLines(g);
			drawLines(lines, g);
		}

		private void configureGraphics(Graphics2D g) {
			g.setRenderingHints((Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints"));
			if(antialiasing) {
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		}
		
		private WrappedLines breakLines(Graphics2D g) {
			Dimension size = getSize();
			float maxWidth = size.width - padding.horizontal();
			AttributedString attributedText = new AttributedString(text, getFont().getAttributes());
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

		private void drawLines(WrappedLines lines, Graphics2D g) {
			float y = originY(lines);
			for(TextLayout line : lines.list) {
				float x = xFor(line);
				y += line.getAscent() - line.getDescent();
				if(shadowColor != null) {
					g.setColor(shadowColor);
					line.draw(g, x-1, y-1);
				}
				g.setColor(getForeground());
				line.draw(g, x, y);
				y += line.getDescent() + lineSpacing;
			}
		}

		private float xFor(TextLayout line) {
			if(alignX == AlignX.LEFT) return padding.left();
			if(alignX == AlignX.CENTER) return (float) (getSize().getWidth() / 2.0 - line.getAdvance() / 2f);
			return (float) (getSize().getWidth() - line.getAdvance()) - padding.right();
		}

		private float originY(WrappedLines lines) {
			if(alignY == AlignY.TOP) return padding.top();
			if(alignY == AlignY.CENTER) return (float) (getSize().getHeight() / 2.0 - lines.height / 2f);
			return (float) (getSize().getHeight() - lines.height - padding.bottom());
		}
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		if(preferredSize == null) {
			Graphics2D g = (Graphics2D) getGraphics();
			FontRenderContext frc = g.getFontRenderContext();
			LineMetrics metrics = getFont().getLineMetrics(text, frc);
			float height = metrics.getAscent() + metrics.getDescent() + padding.vertical();
			preferredSize = new Dimension(0, (int)Math.ceil(height));
		}
		return preferredSize;
	}
	
	private class WrappedLines {
		
		List<TextLayout> list = new ArrayList<TextLayout>();
		float width = 0f;
		float height = 0f;
		
		public void add(TextLayout line) {
			float advance = line.getAdvance();
			if(advance > width) width = advance;
			if(height > 0f) height += lineSpacing;
			height += line.getAscent();
			list.add(line);
		}

	}

}
