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

package org.tomighty.ui.state;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.UiStateChanged;
import org.tomighty.ioc.Inject;
import org.tomighty.ui.UiState;
import org.tomighty.ui.state.pomodoro.Pomodoro;
import org.tomighty.ui.state.pomodoro.PomodoroFinished;

@SuppressWarnings("serial")
public class Gauge extends JComponent implements Subscriber<UiStateChanged> {

	private static final int LIGHT_SIZE = 5;
	private static final int GAP_BETWEEN_LIGHTS = 3;
	private static final Color LIGHT_ON_COLOR_1 = new Color(227, 244, 144);
	private static final Color LIGHT_ON_COLOR_2 = new Color(136, 130, 35);
	private static final Color LIGHT_OFF_COLOR_1 = new Color(60, 60, 60);
	private static final Color LIGHT_OFF_COLOR_2 = new Color(32, 32, 32);
	
	private int finishedPomodoros = 0;
	
	@Inject
	public Gauge(Bus bus) {
		setOpaque(false);
		int width = LIGHT_SIZE * 4 + GAP_BETWEEN_LIGHTS * 3 + 1;
		int height = LIGHT_SIZE + 1;
		setSize(width, height);
		setPreferredSize(getSize());
		bus.subscribe(this, UiStateChanged.class);
	}
	
	@Override
	public void receive(UiStateChanged message) {
		UiState uiState = message.uiState();
		if(uiState instanceof PomodoroFinished) {
			finishedPomodoros++;
		} else if(uiState instanceof Pomodoro) {
			if(finishedPomodoros >= 4) {
				finishedPomodoros = 0;
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x = getWidth() / 2 - getPreferredSize().width / 2;
		for(int index = 0; index < 4; index++) {
			Color startColor;
			Color endColor;
			if(index < finishedPomodoros) {
				startColor = LIGHT_ON_COLOR_1;
				endColor = LIGHT_ON_COLOR_2;
			} else {
				startColor = LIGHT_OFF_COLOR_1;
				endColor = LIGHT_OFF_COLOR_2;
			}
			Point start = new Point(x, 0);
			Point end = new Point(x + LIGHT_SIZE / 2, LIGHT_SIZE / 2);
			Paint paint = new GradientPaint(start, startColor, end, endColor);
			g2d.setPaint(paint);
			g2d.fillOval(x, 0, LIGHT_SIZE, LIGHT_SIZE);
			x += LIGHT_SIZE + GAP_BETWEEN_LIGHTS;
		}
	}

}
