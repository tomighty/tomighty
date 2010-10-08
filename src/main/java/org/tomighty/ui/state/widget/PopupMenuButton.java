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

package org.tomighty.ui.state.widget;

import static java.awt.RenderingHints.*;
import static javax.swing.SwingUtilities.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.tomighty.ui.Colors;

@SuppressWarnings("serial")
public class PopupMenuButton extends JComponent {
	
	private static final Color BG_COLOR_START = new Color(130, 130, 130);
	private static final Color BG_COLOR_END = new Color(44, 44, 44);
	private static final Color BORDER_COLOR = new Color(30, 30, 30);
	
	private JPopupMenu menu;
	private boolean isMouseOver = false;

	public PopupMenuButton(JPopupMenu menu) {
		this.menu = menu;
		setSize(new Dimension(17, 16));
		setPreferredSize(getSize());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new ClickListener());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		
		if(isMouseOver) {
			paintBackground(g2d);
			paintRoundBorder(g);
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

	private void paintRoundBorder(Graphics g) {
		g.setColor(BORDER_COLOR);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 4, 4);
	}

	private void paintBackground(Graphics2D g2d) {
		Graphics2D g2 = (Graphics2D) g2d.create();
		g2.setPaint(new GradientPaint(0, 0, BG_COLOR_START, 0, getHeight(), BG_COLOR_END));
		g2.fillRect(1, 1, getWidth()-2, getHeight()-2);
		g2.dispose();
	}
	
	private void showMenu() {
		menu.show(PopupMenuButton.this, 0, getHeight());
	}
	
	private class ClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(isLeftMouseButton(e)) {
				showMenu();
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			isMouseOver = true;
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			isMouseOver = false;
			repaint();
		}
	}
	
}
