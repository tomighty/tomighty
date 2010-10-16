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

package org.tomighty.ui.state.laf;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import org.tomighty.ui.Colors;

public class SexyArrowButtonUI extends BasicButtonUI {
	
	public static final ButtonUI INSTANCE = new SexyArrowButtonUI();
	
	private static final Color BG_COLOR_START = new Color(130, 130, 130);
	private static final Color BG_COLOR_END = new Color(44, 44, 44);
	private static final Color BORDER_COLOR = new Color(30, 30, 30);
	
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setSize(new Dimension(17, 16));
		c.setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		
		AbstractButton button = (AbstractButton) c;
		ButtonModel model = button.getModel();
		if(model.isRollover()) {
			paintBackground(g2d, button);
			paintRoundBorder(g2d, button);
		}
		
		g.translate(3, 5);
		
		g.setColor(Color.WHITE);
		g.drawLine(1, 0, 5, 4);
		g.drawLine(0, 0, 5, 5);
		g.drawLine(5, 4, 9, 0);
		g.drawLine(5, 5, 10, 0);
		
		g.setColor(Colors.DARK);
		g.drawLine(0, 1, 5, 6);
		g.drawLine(5, 6, 10, 1);
	}

	private void paintRoundBorder(Graphics2D g, AbstractButton b) {
		g.setColor(BORDER_COLOR);
		g.drawRoundRect(0, 0, b.getWidth()-1, b.getHeight()-1, 4, 4);
	}

	private void paintBackground(Graphics2D g, AbstractButton b) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new GradientPaint(0, 0, BG_COLOR_START, 0, b.getHeight(), BG_COLOR_END));
		g2.fillRect(1, 1, b.getWidth()-2, b.getHeight()-2);
		g2.dispose();
	}
	
}
