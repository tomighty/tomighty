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

import static org.tomighty.ui.Colors.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

class Panel extends JPanel {
	
	public Panel() {
		super(new BorderLayout());
		Border border = BorderFactory.createEmptyBorder(6, 6, 6, 6);
		setBorder(border);
	}

	public void setComponent(Component component) {
		removeAll();
		add(component, BorderLayout.CENTER);
		validate();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		paintBackground(g2d);
		drawOuterBorder(g2d);
		drawInnerBorder(g2d);
	}

	private void drawInnerBorder(Graphics2D g2d) {
		g2d.setColor(INNER_BORDER);
		g2d.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
	}

	private void drawOuterBorder(Graphics2D g2d) {
		g2d.setColor(OUTER_BORDER);
		g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	private int paintBackground(Graphics2D g2d) {
		int height = getHeight();
		int half = height / 2;
		paintGradient(0, height, LIGHT, DARK, g2d);
		paintGradient(half, height, DARK, LIGHT, g2d);
		return height;
	}

	private void paintGradient(int startY, int endY, Color startColor, Color endColor, Graphics2D g2d) {
		GradientPaint gp = new GradientPaint(0, startY, startColor, 0, endY, endColor);
		g2d.setPaint(gp);
		g2d.fillRect(0, startY, getWidth(), endY);
	}
}
