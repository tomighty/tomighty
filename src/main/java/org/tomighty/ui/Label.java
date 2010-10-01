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

package org.tomighty.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JComponent;
import javax.swing.UIManager;

//TODO: This class has lots of rough edges

@SuppressWarnings("serial")
public class Label extends JComponent {
	
	private AttributedString text;
	private int margin = 2;
	
	private LineBreakMeasurer lineMeasurer;
	private int paragraphStart;
	private int paragraphEnd;
	
	public Label(String text) {
		setText(text);
		setFont(UIManager.getDefaults().getFont("Label.font"));
	}
	
	public void setText(String text) {
		this.text = new AttributedString(text);
		Font font = getFont();
		if(font != null) {
			updateFontAttributes(font);
		}
		lineMeasurer = null;
		repaint();
	}
	
	@Override
	public void setFont(Font font) {
		updateFontAttributes(font);
		super.setFont(font);
	}

	private void updateFontAttributes(Font font) {
		text.addAttribute(TextAttribute.FAMILY, font.getFamily());
		text.addAttribute(TextAttribute.SIZE, font.getSize2D());
	}
	
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Dimension ps = getPreferredSize();
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float breakWidth = (float)getSize().width;
        float drawPosY = getHeight() / 2 - ps.height / 2 + margin;
        LineBreakMeasurer lm = lineMeasurer(g2d);
		lm.setPosition(paragraphStart);
        while (lm.getPosition() < paragraphEnd) {
            TextLayout layout = lm.nextLayout(breakWidth); //TODO cache layouts until component is resized
            Rectangle2D b = layout.getBounds();
            float drawPosX = getWidth() / 2 - (int)b.getWidth() / 2;
            drawPosY += layout.getAscent();
            g2d.setColor(Colors.DARK);
            layout.draw(g2d, drawPosX, drawPosY);
            g2d.setColor(Color.WHITE);
            layout.draw(g2d, drawPosX+1, drawPosY+1);
            drawPosY += layout.getDescent() + layout.getLeading();
        }
    }
    
    private LineBreakMeasurer lineMeasurer(Graphics2D g2d) {
        if (lineMeasurer == null) {
            AttributedCharacterIterator paragraph = text.getIterator();
            paragraphStart = paragraph.getBeginIndex();
            paragraphEnd = paragraph.getEndIndex();
            FontRenderContext frc = g2d.getFontRenderContext();
            lineMeasurer = new LineBreakMeasurer(paragraph, frc);
        }
        return lineMeasurer;
    }
	
	@Override
	public Dimension getPreferredSize() {
		Graphics2D g2d = (Graphics2D)getGraphics();
        float breakWidth = (float)getSize().width;
        LineBreakMeasurer lm = lineMeasurer(g2d);
		lm.setPosition(paragraphStart);
		int w=0, h=0;
        while (lm.getPosition() < paragraphEnd) {
        	TextLayout layout = lm.nextLayout(breakWidth);
        	Rectangle2D b = layout.getBounds();
        	w = Math.max(w, (int)b.getWidth());
        	h += layout.getAscent() + layout.getDescent() + layout.getLeading();
        }
		return new Dimension(w + margin*2, h + margin*2);
	}
}
